package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.visitors.HasHandlers;

public abstract class JsonSchemaType<Self extends JsonSchemaType<Self>> extends HasHandlers<Self> {
    protected final JavaType javaType;

    protected JsonSchemaType(JavaType javaType) {
        this.javaType = javaType;
    }

    public JavaType getJavaType() {
        return javaType;
    }

    public abstract Self copy();

    protected abstract ObjectNode toJsonSchema();

    public final ObjectNode toJsonSchemaExternal() {
        ObjectNode schema = toJsonSchema();
        handleNode(schema);
        return schema;
    }
}
