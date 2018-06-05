package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;
import io.daonomic.json.schema.Utils;

import java.util.ArrayList;
import java.util.List;

public class PrimitiveType implements JsonSchemaType<PrimitiveType> {
    private final Type type;
    private List<String> enums;
    private String format;

    public PrimitiveType(Type type) {
        this.type = type;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public PrimitiveType copy() {
        PrimitiveType copy = new PrimitiveType(type);
        copy.setEnums(new ArrayList<>(enums));
        copy.setFormat(format);
        return copy;
    }

    @Override
    public ObjectNode toJsonSchema() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("type", type.name().toLowerCase());
        if (enums != null) {
            node.set("enum", Utils.toArrayNode(enums));
        }
        if (format != null) {
            node.put("format", format);
        }
        return node;
    }

    @Override
    public ObjectNode toUiSchema() {
        return null;
    }

    public Type getType() {
        return type;
    }

    public List<String> getEnums() {
        return enums;
    }

    void setEnums(List<String> enums) {
        this.enums = enums;
    }

    public static PrimitiveType fromString(String type) {
        switch (type) {
            case "string":
                return new PrimitiveType(Type.STRING);
            case "number":
                return new PrimitiveType(Type.NUMBER);
            case "integer":
                return new PrimitiveType(Type.NUMBER);//todo int
            case "boolean":
                return new PrimitiveType(Type.BOOLEAN);
        }
        throw new IllegalArgumentException("Unable to handle type " + type);
    }

    public enum Type {
        NUMBER,
        BOOLEAN,
        STRING;
    }
}

