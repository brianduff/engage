package org.dubh.engage;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/** Used to initialize the configuration engine. */
public class ConfigurationEngine {
  private final List<ValueProvider> providers = new ArrayList<>();
  private final List<GeneratedProperties> generatedProperties = new ArrayList<>();

  /** Adds the specified command line arguments to this engine. */
  public ConfigurationEngine withCommandlineArgs(String[] flags) {
    providers.add(new StringMapProvider(new CommandlineFlagParser().parse(flags)));
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

    return true;
  }

  private void printUsage(PrintStream out) {
    out.println("Options:");
    for (GeneratedProperties p : generatedProperties) {
      for (PropertyDescriptor d : p.getPropertyDescriptors()) {
        String optionString = "--" + d.name;
        if (d.type != Boolean.class) {
          optionString += "=<" + getValueDescriptionForHelp(d.type) + ">";
        }
        String defaultString = "";
        if (d.defaultValue != null) {
          defaultString = " [default: " + d.defaultValue + "]";
        }
        out.printf("  %s\t%s%s\n", optionString, d.description, defaultString);
      }
    }
  }

  private String getValueDescriptionForHelp(Class<?> optionType) {
    if (optionType == String.class) {
      return "str";
    } else if (optionType == Integer.class) {
      return "int";
    }
    return "val";
  }
}
