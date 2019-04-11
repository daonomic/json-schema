package io.daonomic.schema.json.annotations;

import io.daonomic.schema.json.JsonSchemaType;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

import java.lang.annotation.Annotation;

public interface TypeAnnotationHandler<A extends Annotation> {
    JsonSchemaType handle(JsonSchemaType jsonSchemaType, A annotation, LabelResolver labels);
}
