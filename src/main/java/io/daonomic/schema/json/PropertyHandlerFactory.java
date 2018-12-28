package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.BeanProperty;

public interface PropertyHandlerFactory {
    LabelResolver getLabelResolver();
    PropertyHandler create(BeanProperty beanProperty);
}
