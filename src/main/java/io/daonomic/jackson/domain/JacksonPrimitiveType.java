package io.daonomic.jackson.domain;

import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;

import java.util.Set;

public class JacksonPrimitiveType implements JacksonType {
    private final Type type;
    private Set<String> enums;
    private JsonValueFormat format;

    public JacksonPrimitiveType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Set<String> getEnums() {
        return enums;
    }

    public void setEnums(Set<String> enums) {
        this.enums = enums;
    }

    public JsonValueFormat getFormat() {
        return format;
    }

    public void setFormat(JsonValueFormat format) {
        this.format = format;
    }

    public enum Type {
        STRING,
        NUMBER,
        BOOLEAN
    }
}
