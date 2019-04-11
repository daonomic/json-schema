package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import io.daonomic.schema.json.HandlerFactory;
import io.daonomic.schema.json.JsonSchemaType;

public class JsonSchemaVisitor extends JsonFormatVisitorWrapper.Base {
    private final ObjectMapper objectMapper;
    private final HandlerFactory handlerFactory;
    private AbstractFormatVisitor current;

    public JsonSchemaVisitor(ObjectMapper objectMapper, HandlerFactory handlerFactory) {
        this.objectMapper = objectMapper;
        this.handlerFactory = handlerFactory;
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType type) {
        return setCurrent(new ObjectFormatVisitor(objectMapper, type, handlerFactory));
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType type) {
        return setCurrent(new IntegerFormatVisitor(objectMapper, type, handlerFactory.getLabelResolver()));
    }

    @Override
    public JsonNumberFormatVisitor expectNumberFormat(JavaType type) {
        return setCurrent(new NumberFormatVisitor(objectMapper, type, handlerFactory.getLabelResolver()));
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType type) {
        return setCurrent(new StringFormatVisitor(objectMapper, type, handlerFactory.getLabelResolver()));
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType type) {
        return setCurrent(new BooleanFormatVisitor(objectMapper, type, handlerFactory.getLabelResolver()));
    }

    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType type) {
        return setCurrent(new ArrayFormatVisitor(objectMapper, type, handlerFactory));
    }

    @Override
    public JsonNullFormatVisitor expectNullFormat(JavaType type) throws JsonMappingException {
        return super.expectNullFormat(type);
    }

    @Override
    public JsonAnyFormatVisitor expectAnyFormat(JavaType type) throws JsonMappingException {
        return super.expectAnyFormat(type);
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type) throws JsonMappingException {
        return super.expectMapFormat(type);
    }

    private <T extends AbstractFormatVisitor> T setCurrent(T current) {
        if (this.current != null) {
            throw new IllegalStateException("Current visitor already set");
        }
        this.current = current;
        return current;
    }

    public JsonSchemaType getSchemaType() {
        if (current == null) {
            throw new IllegalArgumentException("no current visitor set");
        }
        return current.getSchemaType();
    }

    public static JsonSchemaType inspect(JavaType javaType, ObjectMapper objectMapper, HandlerFactory handlerFactory) throws JsonMappingException {
        JsonSchemaVisitor visitor = new JsonSchemaVisitor(objectMapper, handlerFactory);
        objectMapper.acceptJsonFormatVisitor(javaType, visitor);
        return visitor.getSchemaType();
    }
}
