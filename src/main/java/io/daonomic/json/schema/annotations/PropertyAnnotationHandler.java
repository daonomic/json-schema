package io.daonomic.json.schema.annotations;

import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.ObjectType;

import java.lang.annotation.Annotation;

public interface PropertyAnnotationHandler<A extends Annotation> {
    default JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property, A annotation) {
        return handle(property, annotation);
    }

    JsonSchemaProperty handle(JsonSchemaProperty property, A annotation);
}
