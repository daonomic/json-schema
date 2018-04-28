package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.json.schema.PropertyHandlerFactory;

import java.util.Set;

public class ArrayFormatVisitor extends AbstractFormatVisitor<ArrayType> implements JsonArrayFormatVisitor {
    private final PropertyHandlerFactory propertyHandlerFactory;

    protected ArrayFormatVisitor(ObjectMapper objectMapper, PropertyHandlerFactory propertyHandlerFactory) {
        super(objectMapper, new ArrayType());
        this.propertyHandlerFactory = propertyHandlerFactory;
    }

    @Override
    public void itemsFormat(JsonFormatVisitable handler, JavaType elementType) throws JsonMappingException {
        schemaType.setItemType(JsonFormatVisitor.inspect(elementType, objectMapper, propertyHandlerFactory));
    }

    @Override
    public void itemsFormat(JsonFormatTypes format) throws JsonMappingException {
        schemaType.setItemType(PrimitiveType.fromString(format.value()));
    }

    @Override
    public void format(JsonValueFormat format) {

    }

    @Override
    public void enumTypes(Set<String> enums) {

    }
}
