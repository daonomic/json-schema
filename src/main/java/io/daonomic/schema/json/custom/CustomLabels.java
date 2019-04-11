package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.annotations.TypeAnnotationHandlerClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@TypeAnnotationHandlerClass(CustomLabelsAnnotationHandler.class)
public @interface CustomLabels {
    String value() default "DEFAULT";
}
