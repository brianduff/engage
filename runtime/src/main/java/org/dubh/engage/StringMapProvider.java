package org.dubh.engage;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * A generic provider based on a string map. The values are interpreted based on the type of the
 * value being fetched.
 */
final class StringMapProvider implements ValueProvider {
  private final Map<String, String> values;

  StringMapProvider(Map<String, String> values) {
    this.values = Collections.unmodifiableMap(values);
  }

  @Override
  public <T> T getValue(Class<T> type, String name) {
    return convert(type, values.get(name));
  }

  @Override
  public boolean hasValue(String name) {
    return values.containsKey(name);
  }

  @SuppressWarnings("unchecked") // primitive type unboxing casts
  private <T> T convert(Class<T> type, String value) {
    if (value == null) {
      return null;
    }
    if (String.class.isAssignableFrom(type)) {
      return type.cast(value);
    } else if (Integer.class.isAssignableFrom(type)) {
      return type.cast(Integer.parseInt(value));
    } else if (Boolean.class.isAssignableFrom(type)) {
      return type.cast(Boolean.valueOf(value));
    } else if (int.class.isAssignableFrom(type)) {
      return (T) Integer.valueOf(Integer.parseInt(value));
    } else if (Enum.class.isAssignableFrom(type)) {
      return type.cast(valueOf(type, value));
    }
    // TODO(bduff): other types
    return type.cast(value);
  }

  private static Object valueOf(Class<?> enumType, String value) {
    return Arrays.stream(enumType.getEnumConstants())
        .filter(e -> e.toString().equals(value))
        .findFirst()
        .orElse(null);
  }
}
