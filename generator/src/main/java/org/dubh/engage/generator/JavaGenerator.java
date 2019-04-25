package org.dubh.engage.generator;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dubh.engage.json.ConfigurationModelFactory;
import org.dubh.engage.model.ConfigurationModel;
import org.dubh.engage.model.ConfigurationProperty;

/** Generates a .java file given an input json file. */
public class JavaGenerator {
  private final String javaPackage;
  private final String javaClassName;

  JavaGenerator(String javaPackage, String javaClassName) {
    this.javaPackage = javaPackage;
    this.javaClassName = javaClassName;
  }

  public String generate(String json) throws IOException {
    ConfigurationModel model = new ConfigurationModelFactory().fromJson(json);

    // Build an intermediate model to pass into mustache.
    Map<String, Object> mustacheModel = new HashMap<>();
    mustacheModel.put("javaPackage", javaPackage);
    mustacheModel.put("javaClassName", javaClassName);

    List<Map<String, Object>> renderedProperties = new ArrayList<>();
    mustacheModel.put("renderedProperties", renderedProperties);
    for (ConfigurationProperty<?> property : model.getFile().getProperties()) {
      renderedProperties.add(toRenderedProperty(property));
    }

    MustacheFactory mf = new DefaultMustacheFactory();
    Mustache mustache = mf.compile("org/dubh/engage/generator/template/Properties.mustache");
    StringWriter sw = new StringWriter();
    mustache.execute(sw, mustacheModel);

    return sw.toString();
  }

  private static Map<String, Object> toRenderedProperty(ConfigurationProperty<?> property) {
    Map<String, Object> renderedProperty = new HashMap<>();
    renderedProperty.put("property", property);
    renderedProperty.put("javaMethodName", toJavaMethodName(property.getName()));
    renderedProperty.put("javaDefaultValue", toJavaDefaultValue(property.getDefaultValue()));
    return renderedProperty;
  }

  private static String toJavaMethodName(String propertyName) {
    return Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
  }

  private static String toJavaDefaultValue(Object defaultValue) {
    if (defaultValue == null) {
      return "null";
    }
    if (defaultValue instanceof String) {
      return "\"" + defaultValue + "\"";
    }
    return defaultValue.toString();
  }

  public static void main(String[] args) throws Exception {
    Path jsonFile = Paths.get(args[0]);
    Path outJavaFile = Paths.get(args[1]);

    String javaPackage = args[2];
    String javaClassName = outJavaFile.getFileName().toString();
    if (javaClassName.endsWith(".java")) {
      javaClassName = javaClassName.substring(0, javaClassName.length() - ".java".length());
    }

    String jsonString = new String(Files.readAllBytes(jsonFile), StandardCharsets.UTF_8);
    JavaGenerator generator = new JavaGenerator(javaPackage, javaClassName);
    String javaString = generator.generate(jsonString);
    Files.write(outJavaFile, javaString.getBytes(StandardCharsets.UTF_8));
  }
}
