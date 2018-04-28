package io.daonomic.json.schema;

import com.fasterxml.jackson.databind.node.ObjectNode;

public interface JsonNodeHandler {
    void handle(ObjectNode node);
}
