package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.schema.json.HandlerFactory;

import java.util.Set;

public class ArrayFormatVisitor extends AbstractFormatVisitor<ArrayType> implements JsonArrayFormatVisitor {
    private final HandlerFactory handlerFactory;

    protected ArrayFormatVisitor(ObjectMapper objectMapper, JavaType javaType, HandlerFactory handlerFactory) {
        super(objectMapper, javaType, new ArrayType());
        this.handlerFactory = handlerFactory;
    }

    @Override
    public void itemsFormat(JsonFormatVisitable handler, JavaType elementType) throws JsonMappingException {
        schemaType.setItemType(handlerFactory.create(elementType).handle(JsonSchemaVisitor.inspect(elementType, objectMapper, handlerFactory)));
    }

    @Override
    public void itemsFormat(JsonFormatTypes format) {
        schemaType.setItemType(PrimitiveType.fromString(format.value(), handlerFactory.getLabelResolver()));
    }

    @Override
    public void format(JsonValueFormat format) {

    }

    @Override
    public void enumTypes(Set<String> enums) {

    }
}
