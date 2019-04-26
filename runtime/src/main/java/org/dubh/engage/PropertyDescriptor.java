package org.dubh.engage;

/** Metadata about a property available at runtime (for e.g. help). */
public final class PropertyDescriptor {
  final Class<?> type;
  final String name;
  final String description;
  final Object defaultValue;
  final boolean required;

  public PropertyDescriptor(
      Class<?> type, String name, String description, Object defaultValue, boolean required) {
    this.type = type;
    this.name = name;
    this.description = description;
    this.defaultValue = defaultValue;
    this.required = required;
  }
}
