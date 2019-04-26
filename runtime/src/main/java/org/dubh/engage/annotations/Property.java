package org.dubh.engage.annotations;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/** Annotates a field to indicate that it's populated by a property. */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Property {
  String name() default "";
}
