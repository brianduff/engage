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
      String type = jsonProperties.getJSONObject(i).getString("type");
      if (!"string".equals(type)) {
        throw new IllegalStateException();
      }
      String defaultValue = jsonProperties.getJSONObject(i).getString("default");

      properties.add(new ConfigurationProperty<String>(name, PropertyType.STRING, defaultValue));
    }

    ConfigurationFile file = new ConfigurationFile(properties);
    return new ConfigurationModel(file);
  }
}
