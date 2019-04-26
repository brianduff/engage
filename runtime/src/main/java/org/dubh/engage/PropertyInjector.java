package org.dubh.engage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.dubh.engage.annotations.Property;

/** PropertyInjector can inject values into annotated fields automatically. */
public final class PropertyInjector {
  private static final PropertyInjector INSTANCE = new PropertyInjector();

  /** Returns the PropertyInjector. */
  public static PropertyInjector getInstance() {
    return INSTANCE;
  }

  /** A convenience method that injects a single object, and returns it. */
  public <T> T inject(T object) {
    injectImpl(object);
    return object;
  }

  /**
   * Injects properties into the specified objects. This will overwrite any existing values in
   * fields in these objects that correspond to known properties in the PropertyResolver.
   */
  public void inject(Object... objects) {
    injectImpl(objects);
  }

  private void injectImpl(Object... objects) {
    for (Object o : objects) {
      for (Field f : o.getClass().getDeclaredFields()) {
        // Skip final fields, since we can't mutate them.
        if ((f.getModifiers() & Modifier.FINAL) != 0) {
          continue;
        }
        Property propertyAnnotation = getPropertyAnnotation(f);
        if (propertyAnnotation == null) {
          continue;
        }

        Class<?> type = f.getType();
        String name =
            !"".equals(propertyAnnotation.name()) ? propertyAnnotation.name() : f.getName();

        Object value = PropertyResolver.getDefaultInstance().get(type, name, null);
        if (value == null) {
          continue;
        }
        // Make it possible to write the field even if private.
        f.setAccessible(true);
        try {
          f.set(o, value);
        } catch (ReflectiveOperationException e) {
          // TODO(bduff): we shouldn't bomb if we can't reflectively set a field.
          throw new IllegalStateException(e);
        }
      }
    }
  }

  private Property getPropertyAnnotation(Field f) {
    for (Annotation a : f.getAnnotations()) {
      if (a.annotationType() == Property.class) {
        return (Property) a;
      }
    }
    return null;
  }
}
