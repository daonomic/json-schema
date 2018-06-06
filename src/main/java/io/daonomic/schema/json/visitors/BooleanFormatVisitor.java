package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;

import java.util.Set;

public class BooleanFormatVisitor extends AbstractFormatVisitor<PrimitiveType> implements JsonBooleanFormatVisitor {
    public BooleanFormatVisitor(ObjectMapper objectMapper) {
        super(objectMapper, new PrimitiveType(PrimitiveType.Type.BOOLEAN));
    }

    @Override
    public void format(JsonValueFormat format) {
        //todo
    }

    @Override
    public void enumTypes(Set<String> enums) {
        //todo
    }
}
