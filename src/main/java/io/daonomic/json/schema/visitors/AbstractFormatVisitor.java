package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormatVisitor;
import io.daonomic.json.schema.JsonSchemaType;

public abstract class AbstractFormatVisitor<T extends JsonSchemaType> implements JsonValueFormatVisitor, JsonFormatVisitorWithSerializerProvider {
    protected final ObjectMapper objectMapper;
    protected final T schemaType;
    private SerializerProvider provider;

    protected AbstractFormatVisitor(ObjectMapper objectMapper, T schemaType) {
        this.objectMapper = objectMapper;
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
