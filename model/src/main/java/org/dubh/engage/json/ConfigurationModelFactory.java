package org.dubh.engage.json;

import java.util.ArrayList;
import java.util.List;
import org.dubh.engage.model.ConfigurationFile;
import org.dubh.engage.model.ConfigurationModel;
import org.dubh.engage.model.ConfigurationProperty;
import org.dubh.engage.model.PropertyType;
import org.json.JSONArray;
import org.json.JSONObject;

public class ConfigurationModelFactory {

  public ConfigurationModel fromJson(String jsonString) {
    JSONObject json = new JSONObject(jsonString);
    JSONArray jsonProperties = json.getJSONArray("properties");

    List<ConfigurationProperty<?>> properties = new ArrayList<>();

    for (int i = 0; i < jsonProperties.length(); i++) {
      String name = jsonProperties.getJSONObject(i).getString("name");
      String jsonTypeName = jsonProperties.getJSONObject(i).getString("type");

      Object defaultValue = jsonProperties.getJSONObject(i).get("default");

      properties.add(createConfigurationProperty(name, jsonTypeName, defaultValue));
    }

    ConfigurationFile file = new ConfigurationFile(properties);
    return new ConfigurationModel(file);
  }

  private ConfigurationProperty<?> createConfigurationProperty(
      String name, String jsonTypeName, Object defaultValue) {
    PropertyType type = getPropertyType(jsonTypeName);

    switch (type) {
      case STRING:
        return new ConfigurationProperty<String>(name, type, (String) defaultValue);
      case INTEGER:
        return new ConfigurationProperty<Integer>(name, type, (Integer) defaultValue);
    }
    throw new IllegalStateException("Unhandled type " + type);
  }

  private PropertyType getPropertyType(String jsonName) {
    switch (jsonName) {
      case "string":
        return PropertyType.STRING;
      case "int":
        return PropertyType.INTEGER;
    }
    throw new IllegalArgumentException("Invalid property type " + jsonName);
  }
}
