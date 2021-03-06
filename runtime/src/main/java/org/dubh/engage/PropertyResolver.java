package org.dubh.engage;

import java.util.Collections;
import java.util.List;

/** Knows how to resolve properties to values. */
public class PropertyResolver {
  private static volatile PropertyResolver defaultInstance;

  private final List<ValueProvider> valueProviders;

  PropertyResolver(List<ValueProvider> valueProviders) {
    this.valueProviders = Collections.unmodifiableList(valueProviders);
  }

  public <T> T get(Class<T> type, String name, T defaultValue) {
    for (ValueProvider provider : valueProviders) {
      T value = provider.getValue(type, name);
      if (value != null) {
        return value;
      }
    }
    return defaultValue;
  }

  public boolean has(String name) {
    for (ValueProvider provider : valueProviders) {
      if (provider.hasValue(name)) {
        return true;
      }
    }
    return false;
  }

  public static PropertyResolver getDefaultInstance() {
    if (defaultInstance == null) {
      throw new IllegalStateException("PropertyResolver not yet initialized");
    }
    return defaultInstance;
  }

  static void setDefaultInstance(PropertyResolver resolver) {
    defaultInstance = resolver;
  }
}
