package io.daonomic.schema.ui.handlers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.ui.annotations.PropertyHandlerClass;

import java.lang.annotation.Annotation;

public interface UiSchemaPropertyHandler<A extends Annotation> {
    void handle(A annotation, ObjectNode node);

    static Class<? extends UiSchemaPropertyHandler> getByAnnotation(Annotation annotation) {
        PropertyHandlerClass propertyHandlerClass = annotation.annotationType().getAnnotation(PropertyHandlerClass.class);
        if (propertyHandlerClass != null) {
            return propertyHandlerClass.value();
        }
        return null;
    }

}
