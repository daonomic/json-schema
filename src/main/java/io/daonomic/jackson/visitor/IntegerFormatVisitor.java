package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.jackson.domain.JacksonNumberType;
import io.daonomic.jackson.domain.JacksonPrimitiveType;

import java.util.Set;

public class IntegerFormatVisitor extends AbstractTypeVisitor<JacksonNumberType> implements JsonIntegerFormatVisitor {
    IntegerFormatVisitor(ObjectMapper objectMapper) {
        super(new JacksonNumberType(), objectMapper);
    }

    @Override
    public void format(JsonValueFormat format) {
        type.setFormat(format);
    }

    @Override
    public void enumTypes(Set<String> enums) {
        type.setEnums(enums);
    }

    @Override
    public void numberType(JsonParser.NumberType type) {
        this.type.setNumberType(type);
    }
}
