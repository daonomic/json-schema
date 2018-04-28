package io.daonomic.json.schema;

import com.fasterxml.jackson.databind.BeanProperty;

public interface PropertyHandlerFactory {
    PropertyHandler create(BeanProperty beanProperty);
}
