package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.jackson.domain.JacksonPrimitiveType;

import java.util.Set;

public class StringFormatVisitor extends AbstractTypeVisitor<JacksonPrimitiveType> implements JsonStringFormatVisitor {
    StringFormatVisitor(ObjectMapper objectMapper) {
        super(new JacksonPrimitiveType(JacksonPrimitiveType.Type.STRING), objectMapper);
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
