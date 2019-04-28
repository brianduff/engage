package org.dubh.engage.model;

public enum PropertyType {
  BOOLEAN(Boolean.class),
  INTEGER(Integer.class),
  STRING(String.class),
  ENUM(Enum.class);

  private final Class<?> javaType;

  PropertyType(Class<?> javaType) {
    this.javaType = javaType;
  }

  public Class<?> getJavaType() {
    return javaType;
  }

  public String getJavaTypeName() {
    return javaType.getSimpleName();
  }
}
