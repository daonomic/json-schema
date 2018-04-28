package io.daonomic.json.schema;

import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.ObjectType;

import java.util.List;

public class MultiplePropertyHandler implements PropertyHandler {
    private final List<PropertyHandler> delegates;

    public MultiplePropertyHandler(List<PropertyHandler> delegates) {
        this.delegates = delegates;
    }

    @Override
    public JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property) {
        for (PropertyHandler delegate : delegates) {
            property = delegate.handle(objectType, property);
            if (property == null) {
                return null;
            }
        }
        return property;
    }
}
