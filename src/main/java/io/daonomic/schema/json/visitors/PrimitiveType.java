package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.JsonSchemaType;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.Utils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PrimitiveType implements JsonSchemaType<PrimitiveType> {
    private final Type type;
    private final LabelResolver labels;
    private List<String> enums;
    private String format;

    public PrimitiveType(Type type, LabelResolver labels) {
        this.type = type;
        this.labels = labels;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public PrimitiveType copy() {
        PrimitiveType copy = new PrimitiveType(type, labels);
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
            node.set("enumNames", Utils.toArrayNode(enums.stream().map(labels::resolve).collect(toList())));
        }
        if (format != null) {
            node.put("format", format);
        }
        return node;
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

    public static PrimitiveType fromString(String type, LabelResolver labels) {
        switch (type) {
            case "string":
                return new PrimitiveType(Type.STRING, labels);
            case "number":
                return new PrimitiveType(Type.NUMBER, labels);
            case "integer":
                return new PrimitiveType(Type.NUMBER, labels);//todo int
            case "boolean":
                return new PrimitiveType(Type.BOOLEAN, labels);
        }
        throw new IllegalArgumentException("Unable to handle type " + type);
    }

    public enum Type {
        NUMBER,
        BOOLEAN,
        STRING;
    }
}

