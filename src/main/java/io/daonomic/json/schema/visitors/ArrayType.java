package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;

public class ArrayType extends HasHandlers<ArrayType> implements JsonSchemaType {
    private JsonSchemaType itemType;

    public ArrayType() {
    }

    void setItemType(JsonSchemaType itemType) {
        this.itemType = itemType;
    }

    @Override
    public ObjectNode toJsonNode() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("type", "array");
        ObjectNode itemsNode = itemType.toJsonNode();
        handleNode(itemsNode);
        node.set("items", itemsNode);
        return node;
    }
}
