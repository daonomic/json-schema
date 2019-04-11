package io.daonomic.schema.json;

import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.ObjectType;

import java.util.List;

public class MultipleTypeHandler implements TypeHandler {
    private final List<TypeHandler> delegates;

    public MultipleTypeHandler(List<TypeHandler> delegates) {
        this.delegates = delegates;
    }

    @Override
    public JsonSchemaType handle(JsonSchemaType type) {
        for (TypeHandler delegate : delegates) {
            type = delegate.handle(type);
        }
        return type;
    }
}
