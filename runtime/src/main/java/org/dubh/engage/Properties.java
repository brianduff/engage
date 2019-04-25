package org.dubh.engage;

/** Provides simplified access to properties. */
public class Properties {
  private Properties() {}

  public static String getString(String name) {
    return getString(name, null);
  }

  public static String getString(String name, String defaultValue) {
    return PropertyResolver.getDefaultInstance().get(String.class, name, defaultValue);
  }

  public static Integer getInt(String name) {
    return getInt(name, null);
  }

  public static Integer getInt(String name, Integer defaultValue) {
    return PropertyResolver.getDefaultInstance().get(Integer.class, name, defaultValue);
  }
}
