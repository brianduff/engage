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

    List<ConfigurationProperty> properties = new ArrayList<>();

    for (int i = 0; i < jsonProperties.length(); i++) {
      JSONObject jsonProperty = jsonProperties.getJSONObject(i);
      String name = jsonProperty.getString("name");
      String jsonTypeName = jsonProperty.getString("type");
      Object defaultValue = jsonProperty.opt("default");
      String description = jsonProperty.optString("description", null);
      boolean required = jsonProperty.optBoolean("required");

      PropertyType type = getPropertyType(jsonTypeName);
      properties.add(new ConfigurationProperty(name, type, defaultValue, description, required));
    }

    ConfigurationFile file = new ConfigurationFile(properties);
    return new ConfigurationModel(file);
  }

  private PropertyType getPropertyType(String jsonName) {
    switch (jsonName) {
      case "string":
        return PropertyType.STRING;
      case "int":
        return PropertyType.INTEGER;
      case "boolean":
        return PropertyType.BOOLEAN;
    }
    throw new IllegalArgumentException("Invalid property type " + jsonName);
  }
}
