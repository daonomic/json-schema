package io.daonomic.schema.ui.annotations;

import io.daonomic.schema.ui.handlers.UiInlineHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
@HandlerClass(UiInlineHandler.class)
public @interface UiInline {
}

