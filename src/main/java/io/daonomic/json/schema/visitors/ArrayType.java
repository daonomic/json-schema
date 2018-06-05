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
    public ObjectNode toJsonSchema() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("type", "array");
        ObjectNode itemsNode = itemType.toJsonSchema();
        handleNode(itemsNode);
        node.set("items", itemsNode);
        return node;
    }

    @Override
    public ObjectNode toUiSchema() {
        return null;
    }
}
