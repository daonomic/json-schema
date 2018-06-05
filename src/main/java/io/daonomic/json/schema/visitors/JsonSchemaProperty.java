package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;

public class JsonSchemaProperty extends HasHandlers<JsonSchemaProperty> {
    private final String name;
    private final JsonSchemaType type;
    private ObjectNode uiSchema;
    private boolean required;

    public JsonSchemaProperty(String name, JsonSchemaType type, boolean required) {
        this.name = name;
        this.type = type;
        this.required = required;
        this.uiSchema = JsonNodeFactory.instance.objectNode();
    }

    public ObjectNode toJsonNode() {
        ObjectNode result = type.toJsonSchema();
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

    public ObjectNode getUiSchema() {
        return uiSchema;
    }

    public JsonSchemaProperty required(boolean required) {
        this.required = required;
        return this;
    }

    public JsonSchemaProperty copy() {
        return new JsonSchemaProperty(name, type.copy(), required);
    }
}
