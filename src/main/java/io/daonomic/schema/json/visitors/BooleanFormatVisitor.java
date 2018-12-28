package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.schema.json.LabelResolver;

import java.util.Set;

public class BooleanFormatVisitor extends AbstractFormatVisitor<PrimitiveType> implements JsonBooleanFormatVisitor {
    public BooleanFormatVisitor(ObjectMapper objectMapper, LabelResolver labels) {
        super(objectMapper, new PrimitiveType(PrimitiveType.Type.BOOLEAN, labels));
    }

    @Override
    public void format(JsonValueFormat format) {
    }

    @Override
    public void enumTypes(Set<String> enums) {
    }
}
