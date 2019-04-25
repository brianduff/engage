package org.dubh.engage.runtime;

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

  private <T> T convert(Class<T> type, String value) {
    if (String.class.isAssignableFrom(type)) {
      return type.cast(value);
    } else if (Integer.class.isAssignableFrom(type)) {
      return type.cast(Integer.parseInt(value));
    }
    // TODO(bduff): other types
    return type.cast(value);
  }
}
