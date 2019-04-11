package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;

public interface HandlerFactory {
    LabelResolver getLabelResolver();
    TypeHandler create(JavaType javaType);
    PropertyHandler create(BeanProperty beanProperty);
}
