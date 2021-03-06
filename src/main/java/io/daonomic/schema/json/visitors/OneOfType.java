package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.JsonSchemaType;

import java.util.ArrayList;
import java.util.List;

public class OneOfType extends JsonSchemaType<OneOfType> {
    private final List<JsonSchemaType> types = new ArrayList<>();

    public OneOfType() {
        super(null);
    }

    public void addType(JsonSchemaType type) {
        types.add(type);
    }

    public List<JsonSchemaType> getTypes() {
        return types;
    }

    @Override
    public OneOfType copy() {
        OneOfType copy = new OneOfType();
        for (JsonSchemaType type : types) {
            copy.addType(type.copy());
        }
        return copy;
    }

    @Override
    public ObjectNode toJsonSchema() {
        ArrayNode array = JsonNodeFactory.instance.arrayNode();
        for (JsonSchemaType type : types) {
            array.add(type.toJsonSchemaExternal());
        }
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set("oneOf", array);
        return result;
    }
}
