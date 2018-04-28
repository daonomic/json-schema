package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;

public class JsonSchemaProperty extends HasHandlers<JsonSchemaProperty> {
    private final String name;
    private final JsonSchemaType type;
    private boolean required;

    public JsonSchemaProperty(String name, JsonSchemaType type, boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public ObjectNode toJsonNode() {
        ObjectNode result = type.toJsonNode();
        handleNode(result);
        return result;
    }

    public String getName() {
        return name;
    }

    public boolean isRequired() {
        return required;
    }

    public JsonSchemaProperty required(boolean required) {
        this.required = required;
        return this;
    }
}
