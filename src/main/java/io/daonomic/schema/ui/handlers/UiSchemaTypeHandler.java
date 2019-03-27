package io.daonomic.schema.ui.handlers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.ui.annotations.TypeHandlerClass;

import java.lang.annotation.Annotation;

public interface UiSchemaTypeHandler<A extends Annotation, T> {
    default void handle(Class<T> rawClass, A annotation, ObjectNode node, LabelResolver resolver) {
        handle(rawClass, annotation, node);
    }

    default void handle(Class<T> rawClass, A annotation, ObjectNode node) {

    }

    static Class<? extends UiSchemaTypeHandler> getByAnnotation(Annotation annotation) {
        TypeHandlerClass propertyHandlerClass = annotation.annotationType().getAnnotation(TypeHandlerClass.class);
        if (propertyHandlerClass != null) {
            return propertyHandlerClass.value();
        }
        return null;
    }

}
