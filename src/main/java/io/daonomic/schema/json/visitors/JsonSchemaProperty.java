package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.JsonSchemaType;

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
        ObjectNode result = type.toJsonSchemaExternal();
        handleNode(result);
        return result;
    }

    public String getName() {
        return name;
    }

    public JsonSchemaType getType() {
        return type;
    }

    public boolean isRequired() {
        return required;
    }

    public JsonSchemaProperty required(boolean required) {
        this.required = required;
        return this;
    }

    public JsonSchemaProperty copy() {
        return new JsonSchemaProperty(name, type.copy(), required);
    }
}
