package io.daonomic.schema.json.annotations;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.ObjectType;

import java.lang.annotation.Annotation;

public interface PropertyAnnotationHandler<A extends Annotation> {
    default JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property, A annotation, LabelResolver labels) {
        return handle(property, annotation, labels);
    }

    JsonSchemaProperty handle(JsonSchemaProperty property, A annotation, LabelResolver labels);
}
