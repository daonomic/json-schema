package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.daonomic.jackson.domain.JacksonType;

public abstract class AbstractTypeVisitor<T extends JacksonType> extends AbstractVisitor implements JacksonTypeVisitor<T> {
    protected final T type;
    protected final ObjectMapper objectMapper;

    AbstractTypeVisitor(T type, ObjectMapper objectMapper) {
        this.type = type;
        this.objectMapper = objectMapper;
    }

    @Override
    public T getType() {
        return type;
    }
}
