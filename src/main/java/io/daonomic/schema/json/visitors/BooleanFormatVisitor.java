package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonBooleanFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.schema.json.LabelResolver;

import java.util.Set;

public class BooleanFormatVisitor extends AbstractFormatVisitor<PrimitiveType> implements JsonBooleanFormatVisitor {
    public BooleanFormatVisitor(ObjectMapper objectMapper, JavaType javaType, LabelResolver labels) {
        super(objectMapper, javaType, new PrimitiveType(PrimitiveType.Type.BOOLEAN, javaType, labels));
    }

    @Override
    public void format(JsonValueFormat format) {
    }

    @Override
    public void enumTypes(Set<String> enums) {
    }
}
