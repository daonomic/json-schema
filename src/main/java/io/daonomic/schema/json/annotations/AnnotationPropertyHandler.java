package io.daonomic.schema.json.annotations;

import io.daonomic.schema.json.PropertyHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.ObjectType;

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
