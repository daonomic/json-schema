package io.daonomic.jackson.visitor;

import io.daonomic.jackson.domain.JacksonType;

public interface JacksonTypeVisitor<T extends JacksonType> {
    T getType();
}
