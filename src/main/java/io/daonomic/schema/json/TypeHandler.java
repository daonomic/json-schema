package io.daonomic.schema.json;

public interface TypeHandler {
    JsonSchemaType handle(JsonSchemaType type);
}
