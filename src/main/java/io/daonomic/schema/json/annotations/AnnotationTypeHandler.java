package io.daonomic.schema.json.annotations;

import io.daonomic.schema.json.JsonSchemaType;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.TypeHandler;

import java.lang.annotation.Annotation;

public class AnnotationTypeHandler<A extends Annotation> implements TypeHandler {
    private final TypeAnnotationHandler<A> typeAnnotationHandler;
    private final A annotation;
    private final LabelResolver labels;

    public AnnotationTypeHandler(TypeAnnotationHandler<A> typeAnnotationHandler, A annotation, LabelResolver labels) {
        this.typeAnnotationHandler = typeAnnotationHandler;
        this.annotation = annotation;
        this.labels = labels;
    }

    @Override
    public JsonSchemaType handle(JsonSchemaType type) {
        return typeAnnotationHandler.handle(type, annotation, labels);
    }
}
