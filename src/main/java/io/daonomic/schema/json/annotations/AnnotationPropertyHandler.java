package io.daonomic.schema.json.annotations;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.PropertyHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.ObjectType;

import java.lang.annotation.Annotation;

public class AnnotationPropertyHandler<A extends Annotation> implements PropertyHandler {
    private final PropertyAnnotationHandler<A> propertyAnnotationHandler;
    private final A annotation;
    private final LabelResolver labels;

    public AnnotationPropertyHandler(PropertyAnnotationHandler<A> propertyAnnotationHandler, A annotation, LabelResolver labels) {
        this.propertyAnnotationHandler = propertyAnnotationHandler;
        this.annotation = annotation;
        this.labels = labels;
    }

    public JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property) {
        return propertyAnnotationHandler.handle(objectType, property, annotation, labels);
    }
}
