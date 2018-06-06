package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import io.daonomic.jackson.domain.JacksonObjectType;
import io.daonomic.jackson.domain.JacksonProperty;

public class ObjectFormatVisitor extends AbstractTypeVisitor<JacksonObjectType> implements JsonObjectFormatVisitor {
    ObjectFormatVisitor(JavaType javaType, ObjectMapper objectMapper) {
        super(new JacksonObjectType(javaType), objectMapper);
    }

    @Override
    public void property(BeanProperty writer) throws JsonMappingException {
        type.getProperties().add(new JacksonProperty(JacksonVisitor.inspectType(writer.getType(), objectMapper), writer, true));
    }

    @Override
    public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
        throw new IllegalStateException("non-pojo properties are not supported");
    }

    @Override
    public void optionalProperty(BeanProperty writer) throws JsonMappingException {
        type.getProperties().add(new JacksonProperty(JacksonVisitor.inspectType(writer.getType(), objectMapper), writer, false));
    }

    @Override
    public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) {
        throw new IllegalStateException("non-pojo properties are not supported");
    }
}
