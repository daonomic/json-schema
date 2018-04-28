package io.daonomic.json.schema.annotations;

import com.fasterxml.jackson.databind.BeanProperty;
import io.daonomic.json.schema.InstanceCache;
import io.daonomic.json.schema.MultiplePropertyHandler;
import io.daonomic.json.schema.PropertyHandler;
import io.daonomic.json.schema.PropertyHandlerFactory;
import io.daonomic.json.schema.handlers.RequiredPropertyAnnotationHandler;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationPropertyHandlerFactory implements PropertyHandlerFactory {
    private final Map<Class<? extends Annotation>, Class<? extends PropertyAnnotationHandler>> defaultAnnotationHandlers;

    public AnnotationPropertyHandlerFactory() {
        this.defaultAnnotationHandlers = new HashMap<>();
        defaultAnnotationHandlers.put(NotNull.class, RequiredPropertyAnnotationHandler.class);
        defaultAnnotationHandlers.put(NotBlank.class, RequiredPropertyAnnotationHandler.class);
        defaultAnnotationHandlers.put(NotEmpty.class, RequiredPropertyAnnotationHandler.class);
    }

    @SuppressWarnings("unchecked")
    @Override
    public PropertyHandler create(BeanProperty beanProperty) {
        List<HandlerAndOrder> list = new ArrayList<>();
        for (Annotation annotation : beanProperty.getMember().getAllAnnotations().annotations()) {
            Class<? extends PropertyAnnotationHandler> handlerClass = getByAnnotation(annotation);
            if (handlerClass != null) {
                list.add(new HandlerAndOrder(new AnnotationPropertyHandler(InstanceCache.INSTANCE.get(handlerClass), annotation), getOrder(handlerClass)));
            }
        }
        return new MultiplePropertyHandler(list.stream().sorted(Comparator.comparingInt(e2 -> e2.order)).map(e -> e.handler).collect(Collectors.toList()));
    }

    private int getOrder(Class<?> handlerClass) {
        Order order = handlerClass.getAnnotation(Order.class);
        if (order != null) {
            return order.value();
        } else {
            return 0;
        }
    }

    private Class<? extends PropertyAnnotationHandler> getByAnnotation(Annotation annotation) {
        Class<? extends PropertyAnnotationHandler> defaultHandlerClass = defaultAnnotationHandlers.get(annotation.annotationType());
        if (defaultHandlerClass != null) {
            return defaultHandlerClass;
        } else {
            PropertyAnnotationHandlerClass handlerClass = annotation.annotationType().getAnnotation(PropertyAnnotationHandlerClass.class);
            if (handlerClass != null) {
                return handlerClass.value();
            }
            return null;
        }
    }

    private static class HandlerAndOrder {
        private final PropertyHandler handler;
        private final int order;

        public HandlerAndOrder(PropertyHandler handler, int order) {
            this.handler = handler;
            this.order = order;
        }
    }
}
