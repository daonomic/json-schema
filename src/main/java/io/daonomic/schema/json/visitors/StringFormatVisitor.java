package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.schema.json.LabelResolver;

import java.util.ArrayList;
import java.util.Set;

public class StringFormatVisitor extends AbstractFormatVisitor<PrimitiveType> implements JsonStringFormatVisitor {
    public StringFormatVisitor(ObjectMapper objectMapper, JavaType javaType, LabelResolver labels) {
        super(objectMapper, javaType, new PrimitiveType(PrimitiveType.Type.STRING, javaType, labels));
    }

    @Override
    public void format(JsonValueFormat format) {
        schemaType.setFormat(format.toString());
    }

    @Override
    public void enumTypes(Set<String> enums) {
        schemaType.setEnums(new ArrayList<>(enums));
    }
}
