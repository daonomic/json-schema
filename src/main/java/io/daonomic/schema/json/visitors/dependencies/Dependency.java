package io.daonomic.schema.json.visitors.dependencies;

import com.fasterxml.jackson.databind.JsonNode;

public interface Dependency<Self extends Dependency<Self>> {
    Self copy();
    JsonNode toJsonSchema();
}

