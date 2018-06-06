package io.daonomic.schema.json;

import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.ObjectType;

public interface PropertyHandler {
    JsonSchemaProperty handle(ObjectType type, JsonSchemaProperty property);
}
