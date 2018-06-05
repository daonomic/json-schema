package io.daonomic.json.schema.custom;

import io.daonomic.json.schema.annotations.PropertyAnnotationHandlerClass;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@PropertyAnnotationHandlerClass(UiWidgetAnnotationHandler.class)
public @interface UiWidget {
    String value();
    Option[] options() default {};

    @interface Option {
        String key();
        String value();
    }
}

