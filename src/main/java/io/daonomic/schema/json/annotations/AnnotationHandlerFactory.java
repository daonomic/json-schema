package io.daonomic.schema.json.annotations;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import io.daonomic.schema.json.*;
import io.daonomic.schema.json.handlers.EmailPropertyAnnotationHandler;
import io.daonomic.schema.json.handlers.RequiredPropertyAnnotationHandler;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;

public class AnnotationHandlerFactory implements HandlerFactory {
    private final Map<Class<? extends Annotation>, Class<? extends PropertyAnnotationHandler>> defaultPropertyHandlers;
    private final Map<Class<? extends Annotation>, Class<? extends TypeAnnotationHandler>> defaultTypeHandlers;
    private final LabelResolver labels;

    public AnnotationHandlerFactory() {
        this(new DefaultLabelResolver());
    }

    public AnnotationHandlerFactory(LabelResolver labels) {
        this.labels = labels;
        this.defaultPropertyHandlers = new HashMap<>();
        defaultPropertyHandlers.put(NotNull.class, RequiredPropertyAnnotationHandler.class);
        defaultPropertyHandlers.put(NotBlank.class, RequiredPropertyAnnotationHandler.class);
        defaultPropertyHandlers.put(NotEmpty.class, RequiredPropertyAnnotationHandler.class);
        defaultPropertyHandlers.put(Email.class, EmailPropertyAnnotationHandler.class);
        this.defaultTypeHandlers = new HashMap<>();
    }

    @Override
    public LabelResolver getLabelResolver() {
        return labels;
    }

    @SuppressWarnings("unchecked")
    @Override
    public PropertyHandler create(BeanProperty beanProperty) {
        List<HandlerAndOrder<PropertyHandler>> list = new ArrayList<>();
        for (Annotation annotation : beanProperty.getMember().getAllAnnotations().annotations()) {
            Class<? extends PropertyAnnotationHandler> handlerClass = getPropertyAnnotationHandler(annotation);
            if (handlerClass != null) {
                list.add(new HandlerAndOrder(new AnnotationPropertyHandler(InstanceCache.INSTANCE.get(handlerClass), annotation, labels), getOrder(handlerClass)));
            }
        }
        for (Annotation annotation : beanProperty.getType().getRawClass().getAnnotations()) {
            Class<? extends PropertyAnnotationHandler> handlerClass = getPropertyAnnotationHandler(annotation);
            if (handlerClass != null) {
                list.add(new HandlerAndOrder(new AnnotationPropertyHandler(InstanceCache.INSTANCE.get(handlerClass), annotation, labels), getOrder(handlerClass)));
            }
        }
        return new MultiplePropertyHandler(list.stream().sorted(Comparator.comparingInt(e2 -> e2.order)).map(e -> e.handler).collect(Collectors.toList()));
    }

    @SuppressWarnings("unchecked")
    @Override
    public TypeHandler create(JavaType javaType) {
        List<HandlerAndOrder<TypeHandler>> list = new ArrayList<>();
        for (Annotation annotation : javaType.getRawClass().getAnnotations()) {
            Class<? extends TypeAnnotationHandler> handlerClass = getTypeAnnotationHandler(annotation);
            if (handlerClass != null) {
                list.add(new HandlerAndOrder(new AnnotationTypeHandler(InstanceCache.INSTANCE.get(handlerClass), annotation, labels), getOrder(handlerClass)));
            }
        }
        return new MultipleTypeHandler(list.stream().sorted(Comparator.comparingInt(e2 -> e2.order)).map(e -> e.handler).collect(Collectors.toList()));
    }

    private int getOrder(Class<?> handlerClass) {
        Order order = handlerClass.getAnnotation(Order.class);
        if (order != null) {
            return order.value();
        } else {
            return 0;
        }
    }

    private Class<? extends PropertyAnnotationHandler> getPropertyAnnotationHandler(Annotation annotation) {
        Class<? extends PropertyAnnotationHandler> defaultHandlerClass = defaultPropertyHandlers.get(annotation.annotationType());
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

    private Class<? extends TypeAnnotationHandler> getTypeAnnotationHandler(Annotation annotation) {
        Class<? extends TypeAnnotationHandler> defaultHandlerClass = defaultTypeHandlers.get(annotation.annotationType());
        if (defaultHandlerClass != null) {
            return defaultHandlerClass;
        } else {
            TypeAnnotationHandlerClass handlerClass = annotation.annotationType().getAnnotation(TypeAnnotationHandlerClass.class);
            if (handlerClass != null) {
                return handlerClass.value();
            }
            return null;
        }
    }

    private static class HandlerAndOrder<H> {
        private final H handler;
        private final int order;

        HandlerAndOrder(H handler, int order) {
            this.handler = handler;
            this.order = order;
        }
    }
}
