package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.*;
import io.daonomic.jackson.domain.JacksonType;

/**
 * Common visitor for transforming jackson meta-model to some other form
 */
public class JacksonVisitor extends AbstractVisitor implements JsonFormatVisitorWrapper {
    private final ObjectMapper objectMapper;
    private JacksonTypeVisitor current;

    public JacksonVisitor(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public JacksonType getJacksonType() {
        return current.getType();
    }

    @Override
    public JsonObjectFormatVisitor expectObjectFormat(JavaType type) {
        return setCurrent(new ObjectFormatVisitor(type, objectMapper));
    }

    @Override
    public JsonArrayFormatVisitor expectArrayFormat(JavaType type) {
        return setCurrent(new ArrayFormatVisitor(objectMapper));
    }

    @Override
    public JsonStringFormatVisitor expectStringFormat(JavaType type) {
        return setCurrent(new StringFormatVisitor(objectMapper));
    }

    @Override
    public JsonNumberFormatVisitor expectNumberFormat(JavaType type) {
        return setCurrent(new NumberFormatVisitor(objectMapper));
    }

    @Override
    public JsonIntegerFormatVisitor expectIntegerFormat(JavaType type) {
        return setCurrent(new IntegerFormatVisitor(objectMapper));
    }

    @Override
    public JsonBooleanFormatVisitor expectBooleanFormat(JavaType type) {
        return setCurrent(new BooleanFormatVisitor(objectMapper));
    }

    @Override
    public JsonNullFormatVisitor expectNullFormat(JavaType type) {
        return null;
    }

    @Override
    public JsonAnyFormatVisitor expectAnyFormat(JavaType type) {
        return null;
    }

    @Override
    public JsonMapFormatVisitor expectMapFormat(JavaType type) {
        return null;
    }

    private <T extends JacksonTypeVisitor> T setCurrent(T current) {
        if (this.current != null) {
            throw new IllegalStateException("current visitor is already set");
        }
        this.current = current;
        return current;
    }

    public static JacksonType inspectType(JavaType type, ObjectMapper objectMapper) throws JsonMappingException {
        JacksonVisitor visitor = new JacksonVisitor(objectMapper);
        objectMapper.acceptJsonFormatVisitor(type, visitor);
        return visitor.getJacksonType();
    }
}

