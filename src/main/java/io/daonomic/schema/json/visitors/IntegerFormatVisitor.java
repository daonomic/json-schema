package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonIntegerFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;

import java.util.Set;

public class IntegerFormatVisitor extends AbstractFormatVisitor<PrimitiveType> implements JsonIntegerFormatVisitor {

    public IntegerFormatVisitor(ObjectMapper objectMapper) {
        super(objectMapper, new PrimitiveType(PrimitiveType.Type.NUMBER));
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
