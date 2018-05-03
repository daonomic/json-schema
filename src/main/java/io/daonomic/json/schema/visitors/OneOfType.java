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

    public List<JsonSchemaType> getTypes() {
        return types;
    }

    @Override
    public ObjectNode toJsonNode() {
        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        for (JsonSchemaType type : types) {
            array.add(type.toJsonNode());
        }
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set("oneOf", array);
        return result;
    }
}
