package org.dubh.engage.model;

import java.util.Collections;
import java.util.List;

public class ConfigurationFile {
  private final List<ConfigurationProperty> properties;
  private final List<ConfigurationEnum> enums;

  public ConfigurationFile(List<ConfigurationProperty> properties, List<ConfigurationEnum> enums) {
    this.properties = Collections.unmodifiableList(properties);
    this.enums = Collections.unmodifiableList(enums);
  }

  public List<ConfigurationProperty> getProperties() {
    return properties;
  }

  public List<ConfigurationEnum> getEnums() {
    return enums;
  }

  public ConfigurationEnum getEnum(String name) {
    // TODO(bduff): use a map.
    for (ConfigurationEnum e : enums) {
      if (e.getName().equals(name)) {
        return e;
      }
    }
    throw new IllegalArgumentException("Unknown enum name " + name);
  }
}
