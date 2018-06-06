package io.daonomic.schema.ui.handlers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.ui.annotations.HandlerClass;

import java.lang.annotation.Annotation;

public interface UiSchemaPropertyHandler<A extends Annotation> {
    void handle(A annotation, ObjectNode node);

    static Class<? extends UiSchemaPropertyHandler> getByAnnotation(Annotation annotation) {
        HandlerClass handlerClass = annotation.annotationType().getAnnotation(HandlerClass.class);
        if (handlerClass != null) {
            return handlerClass.value();
        }
        return null;
    }

}
