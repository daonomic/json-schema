package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;

public class ArrayType implements JsonSchemaType {
    private JsonSchemaType itemType;

    public ArrayType() {
    }

    void setItemType(JsonSchemaType itemType) {
        this.itemType = itemType;
    }

    @Override
    public ObjectNode toJsonNode(JsonNodeFactory nodeFactory) {
        ObjectNode node = nodeFactory.objectNode();
        node.put("type", "array");
        node.set("items", itemType.toJsonNode(nodeFactory));
        return node;
    }
}
