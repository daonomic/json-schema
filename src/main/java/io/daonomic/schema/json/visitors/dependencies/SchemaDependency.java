package io.daonomic.schema.json.visitors.dependencies;

import com.fasterxml.jackson.databind.JsonNode;
import io.daonomic.schema.json.JsonSchemaType;

public class SchemaDependency implements Dependency<SchemaDependency> {
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
    public SchemaDependency copy() {
        return new SchemaDependency(schemaType.copy());
    }

    @Override
    public JsonNode toJsonSchema() {
        return schemaType.toJsonSchemaExternal();
    }
}
