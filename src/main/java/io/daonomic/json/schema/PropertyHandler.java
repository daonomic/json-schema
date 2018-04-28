package io.daonomic.json.schema;

import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.ObjectType;

public interface PropertyHandler {
    JsonSchemaProperty handle(ObjectType type, JsonSchemaProperty property);
}
