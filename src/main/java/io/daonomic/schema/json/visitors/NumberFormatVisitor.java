package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonNumberFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.schema.json.LabelResolver;

import java.util.Set;

public class NumberFormatVisitor extends AbstractFormatVisitor<PrimitiveType> implements JsonNumberFormatVisitor {

    public NumberFormatVisitor(ObjectMapper objectMapper, JavaType javaType, LabelResolver labels) {
        super(objectMapper, javaType, new PrimitiveType(PrimitiveType.Type.NUMBER, javaType, labels));
    }

    @Override
    public void format(JsonValueFormat format) {
        //todo
    }

    @Override
    public void enumTypes(Set<String> enums) {
        //todo
    }

    @Override
    public void numberType(JsonParser.NumberType type) {
        //todo
    }
}
