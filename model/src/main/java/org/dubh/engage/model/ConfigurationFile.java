package org.dubh.engage.model;

import java.util.Collections;
import java.util.List;

public class ConfigurationFile {
  private final List<ConfigurationProperty> properties;

  public ConfigurationFile(List<ConfigurationProperty> properties) {
    this.properties = Collections.unmodifiableList(properties);
  }

  public List<ConfigurationProperty> getProperties() {
    return properties;
  }
}
