package io.daonomic.json.schema.visitors.dependencies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.daonomic.json.schema.JsonSchemaType;

import java.util.ArrayList;
import java.util.List;

public interface Dependency {
    JsonNode toJsonNode(JsonNodeFactory factory);
}

