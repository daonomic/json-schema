package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;
import io.daonomic.json.schema.Utils;

import java.util.List;

public class PrimitiveType implements JsonSchemaType {
    private final Type type;
    private List<String> enums;

    public PrimitiveType(Type type) {
        this.type = type;
    }

    @Override
    public ObjectNode toJsonNode(JsonNodeFactory factory) {
        ObjectNode node = factory.objectNode();
        node.put("type", type.name().toLowerCase());
        if (enums != null) {
            node.set("enum", Utils.toArrayNode(factory, enums));
        }
        return node;
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

