package org.dubh.engage.model;

import java.util.Objects;

public class ConfigurationProperty {
  private final String name;
  private final PropertyType type;
  private final Object defaultValue;
  private final String description;
  private final boolean required;

  public ConfigurationProperty(
      String name, PropertyType type, Object defaultValue, String description, boolean required) {
    this.name = name;
    this.type = type;
    this.defaultValue = defaultValue;
    this.description = description;
    this.required = required;
  }

  public String getName() {
    return name;
  }

  public PropertyType getType() {
    return type;
  }

  public Object getDefaultValue() {
    return defaultValue;
  }

  public String getDescription() {
    return description;
  }

  public boolean isRequired() {
    return required;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof ConfigurationProperty)) {
      return false;
    }
    ConfigurationProperty other = (ConfigurationProperty) o;
    return Objects.equals(name, other.name) && Objects.equals(type, other.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, type);
  }
}
