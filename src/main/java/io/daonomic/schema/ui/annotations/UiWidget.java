package io.daonomic.schema.ui.annotations;

import io.daonomic.schema.ui.handlers.UiWidgetHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@PropertyHandlerClass(UiWidgetHandler.class)
public @interface UiWidget {
    String value();
    Option[] options() default {};

    @interface Option {
        String key();
        String value();
    }
}

