package io.daonomic.json.schema.custom;

import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;

import java.lang.annotation.Annotation;

public class IgnoreAnnotationHandler implements PropertyAnnotationHandler<Annotation> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Annotation annotation) {
        return null;
    }
}
