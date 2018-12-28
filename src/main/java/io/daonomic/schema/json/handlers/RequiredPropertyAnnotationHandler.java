package io.daonomic.schema.json.handlers;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

import java.lang.annotation.Annotation;

public class RequiredPropertyAnnotationHandler implements PropertyAnnotationHandler<Annotation> {

    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Annotation annotation, LabelResolver labels) {
        return property.required(true);
    }
}
