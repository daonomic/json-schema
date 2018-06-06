package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.jackson.domain.JacksonPrimitiveType;

import java.util.Set;

public class BooleanFormatVisitor extends AbstractTypeVisitor<JacksonPrimitiveType> implements JsonBooleanFormatVisitor {
    BooleanFormatVisitor(ObjectMapper objectMapper) {
        super(new JacksonPrimitiveType(JacksonPrimitiveType.Type.BOOLEAN), objectMapper);
    }

    @Override
    public void format(JsonValueFormat format) {
        type.setFormat(format);
    }

    @Override
    public void enumTypes(Set<String> enums) {
        type.setEnums(enums);
    }
}
