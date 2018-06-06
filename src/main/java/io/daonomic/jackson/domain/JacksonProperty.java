package io.daonomic.jackson.domain;

import com.fasterxml.jackson.databind.BeanProperty;

public class JacksonProperty {
    private final JacksonType type;
    private final BeanProperty beanProperty;
    private boolean required;

    public JacksonProperty(JacksonType type, BeanProperty beanProperty, boolean required) {
        this.type = type;
        this.beanProperty = beanProperty;
        this.required = required;
    }

    public String getName() {
        return beanProperty.getName();
    }

    public BeanProperty getBeanProperty() {
        return beanProperty;
    }

    public JacksonType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }
}
