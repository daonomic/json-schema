package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonMapFormatVisitor;
import io.daonomic.jackson.domain.JacksonMapType;

public class MapFormatVisitor extends AbstractTypeVisitor<JacksonMapType> implements JsonMapFormatVisitor {
    MapFormatVisitor(ObjectMapper objectMapper) {
        super(new JacksonMapType(), objectMapper);
    }

    @Override
    public void keyFormat(JsonFormatVisitable handler, JavaType keyType) throws JsonMappingException {
        type.setKeyType(JacksonVisitor.inspectType(keyType, objectMapper));
    }

    @Override
    public void valueFormat(JsonFormatVisitable handler, JavaType valueType) throws JsonMappingException {
        type.setValueType(JacksonVisitor.inspectType(valueType, objectMapper));
    }
}
