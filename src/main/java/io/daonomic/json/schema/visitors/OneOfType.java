package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;

import java.util.ArrayList;
import java.util.List;

public class OneOfType implements JsonSchemaType {
    private final List<JsonSchemaType> types = new ArrayList<>();

    public void addType(JsonSchemaType type) {
        types.add(type);
    }

    @Override
    public ObjectNode toJsonNode(JsonNodeFactory factory) {
        ArrayNode array = factory.arrayNode();
        for (JsonSchemaType type : types) {
            array.add(type.toJsonNode(factory));
        }
        ObjectNode result = factory.objectNode();
        result.set("oneOf", array);
        return result;
    }
}
