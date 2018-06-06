package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.JsonSchemaType;

public class ArrayType extends HasHandlers<ArrayType> implements JsonSchemaType<ArrayType> {
    private JsonSchemaType itemType;

    public ArrayType() {
    }

    void setItemType(JsonSchemaType itemType) {
        this.itemType = itemType;
    }

    @Override
    public ArrayType copy() {
        ArrayType copy = new ArrayType();
        copy.setItemType(itemType.copy());
        return copy;
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
}
