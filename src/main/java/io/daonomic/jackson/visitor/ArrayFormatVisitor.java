package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import io.daonomic.jackson.domain.JacksonArrayType;

public class ArrayFormatVisitor extends AbstractTypeVisitor<JacksonArrayType> implements JsonArrayFormatVisitor {
    ArrayFormatVisitor(ObjectMapper objectMapper) {
        super(new JacksonArrayType(), objectMapper);
    }

    @Override
    public void itemsFormat(JsonFormatVisitable handler, JavaType elementType) throws JsonMappingException {
        type.setItemType(JacksonVisitor.inspectType(elementType, objectMapper));
    }

    @Override
    public void itemsFormat(JsonFormatTypes format) throws JsonMappingException {
        //todo
    }
}
