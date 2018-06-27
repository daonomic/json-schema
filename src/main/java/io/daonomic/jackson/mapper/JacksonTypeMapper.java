package io.daonomic.jackson.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.daonomic.jackson.domain.*;
import io.daonomic.jackson.visitor.JacksonVisitor;

public class JacksonTypeMapper<T, Object extends T, Array extends T, Number extends T, Primitive extends T, Map extends T> {
    private final JacksonMapper<T, Object, Array, Number, Primitive, Map> mapper;

    public JacksonTypeMapper(JacksonMapper<T, Object, Array, Number, Primitive, Map> mapper) {
        this.mapper = mapper;
    }

    public T map(JavaType javaType, ObjectMapper objectMapper) throws JsonMappingException {
        JacksonType jacksonType = JacksonVisitor.inspectType(javaType, objectMapper);
        return mapJacksonType(jacksonType);
    }

    public T mapJacksonType(JacksonType jacksonType) {
        if (jacksonType instanceof JacksonObjectType) {
            return mapper.fromObject((JacksonObjectType) jacksonType, this);
        } else if (jacksonType instanceof JacksonArrayType) {
            return mapper.fromArray((JacksonArrayType) jacksonType, this);
        } else if (jacksonType instanceof JacksonNumberType) {
            return mapper.fromNumber((JacksonNumberType) jacksonType);
        } else if (jacksonType instanceof JacksonPrimitiveType) {
            return mapper.fromPrimitive((JacksonPrimitiveType) jacksonType);
        } else if (jacksonType instanceof JacksonMapType) {
            return mapper.fromMap((JacksonMapType) jacksonType);
        } else {
            throw new IllegalStateException("should never happen all JacksonType subtypes should be supported");
        }
    }
}
