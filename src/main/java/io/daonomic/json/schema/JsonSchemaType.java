package io.daonomic.json.schema;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonSchemaType {
    ObjectNode toJsonNode(JsonNodeFactory factory);
}
