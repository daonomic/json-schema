package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonNodeHandler {
    void handle(ObjectNode node);
}
