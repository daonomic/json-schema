package io.daonomic.json.schema.annotations;

import io.daonomic.json.schema.PropertyHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.ObjectType;

import java.lang.annotation.Annotation;

public class AnnotationPropertyHandler<A extends Annotation> implements PropertyHandler {
    private final PropertyAnnotationHandler<A> propertyAnnotationHandler;
    private final A annotation;

    public AnnotationPropertyHandler(PropertyAnnotationHandler<A> propertyAnnotationHandler, A annotation) {
        this.propertyAnnotationHandler = propertyAnnotationHandler;
        this.annotation = annotation;
    }

    public JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property) {
        return propertyAnnotationHandler.handle(objectType, property, annotation);
    }
}
