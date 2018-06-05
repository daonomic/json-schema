package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;

public class NotType implements JsonSchemaType {
    private final JsonSchemaType type;

    public NotType(JsonSchemaType type) {
        this.type = type;
    }

    @Override
    public ObjectNode toJsonSchema() {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set("not", type.toJsonSchema());
        return result;
    }

    @Override
    public ObjectNode toUiSchema() {
        return null;
    }
}
