package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonSchemaType<Self extends JsonSchemaType<Self>> {
    Self copy();
    ObjectNode toJsonSchema();
}
