package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor;
import io.daonomic.schema.json.JsonSchemaType;

public abstract class AbstractFormatVisitor<T extends JsonSchemaType> implements JsonValueFormatVisitor, JsonFormatVisitorWithSerializerProvider {
    protected final ObjectMapper objectMapper;
    protected final JavaType javaType;
    protected final T schemaType;
    private SerializerProvider provider;

    protected AbstractFormatVisitor(ObjectMapper objectMapper, JavaType javaType, T schemaType) {
        this.objectMapper = objectMapper;
        this.javaType = javaType;
        this.schemaType = schemaType;
    }

    public T getSchemaType() {
        return schemaType;
    }

    @Override
    public SerializerProvider getProvider() {
        return provider;
    }

    @Override
    public void setProvider(SerializerProvider provider) {
        this.provider = provider;
    }
}
