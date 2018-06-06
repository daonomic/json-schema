package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.BeanProperty;

public interface PropertyHandlerFactory {
    PropertyHandler create(BeanProperty beanProperty);
}
