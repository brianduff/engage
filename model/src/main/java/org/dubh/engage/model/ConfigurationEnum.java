package org.dubh.engage.model;

public class ConfigurationEnum {
  private final String name;
  private final String[] values;

  public ConfigurationEnum(String name, String[] values) {
    this.name = name;
    this.values = values;
  }

  public String getName() {
    return name;
  }

  String[] getValues() {
    return values;
  }
}
