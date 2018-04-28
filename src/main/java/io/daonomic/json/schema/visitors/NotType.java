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
    public ObjectNode toJsonNode() {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set("not", type.toJsonNode());
        return result;
    }
}