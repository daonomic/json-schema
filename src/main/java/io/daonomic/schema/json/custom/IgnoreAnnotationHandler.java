package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

import java.lang.annotation.Annotation;

public class IgnoreAnnotationHandler implements PropertyAnnotationHandler<Annotation> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Annotation annotation) {
        return null;
    }
}
