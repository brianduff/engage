package org.dubh.engage.json;

import java.util.ArrayList;
import java.util.List;
import org.dubh.engage.model.ConfigurationEnum;
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
    List<ConfigurationEnum> enums = new ArrayList<>();

    for (int i = 0; i < jsonProperties.length(); i++) {
      JSONObject jsonProperty = jsonProperties.getJSONObject(i);
      String name = jsonProperty.getString("name");
      String jsonTypeName = jsonProperty.getString("type");
      Object defaultValue = jsonProperty.opt("default");
      String description = jsonProperty.optString("description", null);
      boolean required = jsonProperty.optBoolean("required");

      if ("enum".equals(jsonTypeName)) {
        JSONArray allowedValuesJson = jsonProperty.getJSONArray("allowed_values");
        List<String> allowedValues = new ArrayList<>();
        for (int j = 0; j < allowedValuesJson.length(); j++) {
          allowedValues.add(allowedValuesJson.getString(j));
        }
        enums.add(new ConfigurationEnum(name, allowedValues.toArray(new String[0])));
      }

      PropertyType type = getPropertyType(jsonTypeName);
      properties.add(new ConfigurationProperty(name, type, defaultValue, description, required));
    }

    ConfigurationFile file = new ConfigurationFile(properties, enums);
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
      case "enum":
        return PropertyType.ENUM;
    }
    throw new IllegalArgumentException("Invalid property type " + jsonName);
  }
}
