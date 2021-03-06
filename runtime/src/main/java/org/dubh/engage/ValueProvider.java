package org.dubh.engage;

/** Can provide the value of a property. */
interface ValueProvider {
  /** Returns the value of the given name. May return null if the value is unresolved. */
  <T> T getValue(Class<T> type, String name);

  boolean hasValue(String name);
}
