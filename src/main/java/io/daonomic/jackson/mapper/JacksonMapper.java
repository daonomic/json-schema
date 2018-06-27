package io.daonomic.jackson.mapper;

import io.daonomic.jackson.domain.*;

public interface JacksonMapper<T, Object extends T, Array extends T, Number extends T, Primitive extends T, Map extends T> {
    Object fromObject(JacksonObjectType object, JacksonTypeMapper<T, Object, Array, Number, Primitive, Map> mapper);
    Array fromArray(JacksonArrayType array, JacksonTypeMapper<T, Object, Array, Number, Primitive, Map> mapper);
    Number fromNumber(JacksonNumberType number);
    Primitive fromPrimitive(JacksonPrimitiveType primitive);
    Map fromMap(JacksonMapType map);
}
