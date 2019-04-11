package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.JsonSchemaType;

public class NotType extends JsonSchemaType<NotType> {
    private final JsonSchemaType type;

    public NotType(JsonSchemaType type) {
        super(null);
        this.type = type;
    }

    @Override
    public NotType copy() {
        return new NotType(type.copy());
    }

    @Override
    public ObjectNode toJsonSchema() {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        result.set("not", type.toJsonSchemaExternal());
        return result;
    }
}
