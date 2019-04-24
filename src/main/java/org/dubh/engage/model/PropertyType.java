package org.dubh.engage.model;

public enum PropertyType {
  STRING("String");

  private final String javaTypeName;

  PropertyType(String javaTypeName) {
    this.javaTypeName = javaTypeName;
  }

  public String getJavaTypeName() {
    return javaTypeName;
  }
}
