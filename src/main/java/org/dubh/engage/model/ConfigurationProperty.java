package org.dubh.engage.model;

import java.util.Objects;

public class ConfigurationProperty<T> {
  private final String name;
  private final PropertyType type;
  private final T defaultValue;

  public ConfigurationProperty(String name, PropertyType type, T defaultValue) {
    this.name = name;
    this.type = type;
    this.defaultValue = defaultValue;
  }

  public String getName() {
    return name;
  }

  public PropertyType getType() {
    return type;
  }

  public T getDefaultValue() {
    return defaultValue;
  }

  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ConfigurationProperty)) {
      return false;
    }
    ConfigurationProperty<?> other = (ConfigurationProperty<?>) o;
    return Objects.equals(name, other.name)
        && Objects.equals(type, other.type)
        && Objects.equals(defaultValue, other.defaultValue);
  }
}
