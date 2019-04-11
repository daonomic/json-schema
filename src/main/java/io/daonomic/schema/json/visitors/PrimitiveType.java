package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.JsonSchemaType;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.Utils;
import io.daonomic.schema.json.custom.JsonSchemaIgnore;
import io.daonomic.schema.json.custom.EnumDescriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PrimitiveType extends JsonSchemaType<PrimitiveType> {
    private final Type type;
    private final JavaType javaType;
    private final LabelResolver labels;
    private List<String> enums;
    private String format;

    public PrimitiveType(Type type, JavaType javaType, LabelResolver labels) {
        super(javaType);
        this.type = type;
        this.javaType = javaType;
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
        PrimitiveType copy = new PrimitiveType(type, javaType, labels);
        copy.setEnums(new ArrayList<>(enums));
        copy.setFormat(format);
        return copy;
    }

    @Override
    public ObjectNode toJsonSchema() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("type", type.name().toLowerCase());
        if (enums != null) {

            final List<String> finalEnums;
            if (javaType.getRawClass().isEnum()) {
                Enum[] values = ((Class<Enum>) javaType.getRawClass()).getEnumConstants();
                finalEnums = Arrays.stream(values).filter(it -> {
                    try {
                        return javaType.getRawClass().getField(it.name()).getAnnotation(JsonSchemaIgnore.class) == null;
                    } catch (NoSuchFieldException e) {
                        return false;
                    }
                }).map(Enum::name).collect(toList());
            } else {
                finalEnums = enums;
            }

            node.set("enum", Utils.toArrayNode(finalEnums));
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
                return new PrimitiveType(Type.STRING, null, labels);
            case "number":
                return new PrimitiveType(Type.NUMBER, null, labels);
            case "integer":
                return new PrimitiveType(Type.NUMBER, null, labels);//todo int
            case "boolean":
                return new PrimitiveType(Type.BOOLEAN, null, labels);
        }
        throw new IllegalArgumentException("Unable to handle type " + type);
    }

    public enum Type {
        NUMBER,
        BOOLEAN,
        STRING;
    }
}

