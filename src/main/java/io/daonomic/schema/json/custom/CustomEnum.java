package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.annotations.PropertyAnnotationHandlerClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@PropertyAnnotationHandlerClass(CustomEnumAnnotationHandler.class)
public @interface CustomEnum {
    String[] value();
    String labelPrefix() default "";
}
