package org.dubh.engage;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Collections;

/** Used to initialize the configuration engine. */
public class ConfigurationEngine {
  private final List<ValueProvider> providers = new ArrayList<>();
  private final List<GeneratedProperties> generatedProperties = new ArrayList<>();
  private List<String> remainingArgs = Collections.emptyList();

  /** Adds the specified command line arguments to this engine. */
  public ConfigurationEngine withCommandlineArgs(String[] flags) {
    CommandlineFlagParser.ParsedResults results = new CommandlineFlagParser().parse(flags);
    remainingArgs = Collections.unmodifiableList(results.remainingArgs);
    providers.add(new StringMapProvider(results.formalArgs));
    return this;
  }

  /**
   * Register all generated properties in this method if you want them to show up in commandline
   * help.
   */
  public ConfigurationEngine withGeneratedProperties(GeneratedProperties... properties) {
    for (GeneratedProperties p : properties) {
      generatedProperties.add(p);
    }
    return this;
  }

  /**
   * Initializes the property resolver. After calling this method, {@link
   * PropertyResolver#getDefaultInstance} will resolve to a non-null value.
   */
  public ConfigurationEngine initialize() {
    PropertyResolver.setDefaultInstance(new PropertyResolver(providers));
    return this;
  }

  /**
   * Checks that all required options have been configured and handles --help. Returns true if all
   * is ok, false otherwise.
   */
  public boolean checkUsage() {
    if (Properties.getBoolean("help", false)) {
      printUsage(System.out);
      return false;
    }

    boolean hasErrors = false;

    // Check if there are any unsatisfied required properties.
    Set<PropertyDescriptor> missing = getMissingRequiredProperties();
    if (!missing.isEmpty()) {
      for (PropertyDescriptor p : missing) {
        System.err.println("Missing required option: " + toOptionString(p));
      }
      System.err.println();
      hasErrors = true;
    }

    // Check if there are any enum types with invalid values.
    for (GeneratedProperties p : generatedProperties) {
      for (PropertyDescriptor d : p.getPropertyDescriptors()) {
        if (Enum.class.isAssignableFrom(d.type)) {
          String value =
              PropertyResolver.getDefaultInstance()
                  .get(String.class, d.name, (String) d.defaultValue);
          // It's ok for it to be null.
          if (value == null) {
            continue;
          }
          List<String> validEnumValues = getEnumValues(d.type);
          if (!validEnumValues.contains(value)) {
            System.err.printf(
                "Invalid value '%s' for option: %s. Must be: <%s>\n",
                value, d.name, getValueDescriptionForHelp(d));
            hasErrors = true;
          }
        }
      }
    }

    if (hasErrors) {
      printUsage(System.err);
      return false;
    }

    return true;
  }

  /**
   * Returns the set of non-formal args (i.e. args not starting with --).
   */
  public List<String> getUnprocessedArgs() {
    return remainingArgs;
  }

  private Set<PropertyDescriptor> getMissingRequiredProperties() {
    Set<PropertyDescriptor> missing = new HashSet<>();
    for (GeneratedProperties p : generatedProperties) {
      for (PropertyDescriptor d : p.getPropertyDescriptors()) {
        if (d.required) {
          if (!PropertyResolver.getDefaultInstance().has(d.name) && d.defaultValue == null) {
            missing.add(d);
          }
        }
      }
    }
    return missing;
  }

  private String toOptionString(PropertyDescriptor p) {
    String optionString = "--" + p.name;
    if (p.type != Boolean.class) {
      optionString += "=<" + getValueDescriptionForHelp(p) + ">";
    }
    return optionString;
  }

  private void printUsage(PrintStream out) {
    out.println("Options:");
    for (GeneratedProperties p : generatedProperties) {
      for (PropertyDescriptor d : p.getPropertyDescriptors()) {
        String optionString = toOptionString(d);
        String defaultString = "";
        if (d.defaultValue != null) {
          defaultString = " [default: " + d.defaultValue + "]";
        }
        String descriptionString = d.name;
        if (d.description != null && d.description.length() > 0) {
          descriptionString = d.description;
        }
        out.printf("  %s\t%s%s\n", optionString, descriptionString, defaultString);
      }
    }
  }

  private String getValueDescriptionForHelp(PropertyDescriptor p) {
    if (p.type == String.class) {
      return "str";
    } else if (p.type == Integer.class) {
      return "int";
    } else if (Enum.class.isAssignableFrom(p.type)) {
      return String.join("|", getEnumValues(p.type));
    }
    return "val";
  }

  private List<String> getEnumValues(Class<?> enumClass) {
    Object[] values = enumClass.getEnumConstants();
    List<String> stringValues = new ArrayList<>();
    for (Object o : values) {
      stringValues.add(o.toString());
    }
    return stringValues;
  }
}
