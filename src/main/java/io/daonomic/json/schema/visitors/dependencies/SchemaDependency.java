package io.daonomic.json.schema.visitors.dependencies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.daonomic.json.schema.JsonSchemaType;

public class SchemaDependency implements Dependency {
    private JsonSchemaType schemaType;

    public SchemaDependency(JsonSchemaType schemaType) {
        this.schemaType = schemaType;
    }

    public JsonSchemaType getSchemaType() {
        return schemaType;
    }

    public void setSchemaType(JsonSchemaType schemaType) {
        this.schemaType = schemaType;
    }

    @Override
    public JsonNode toJsonNode(JsonNodeFactory factory) {
        return schemaType.toJsonNode(factory);
    }
}
