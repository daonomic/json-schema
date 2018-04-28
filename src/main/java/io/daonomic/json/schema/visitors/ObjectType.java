package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;
import io.daonomic.json.schema.Utils;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class ObjectType extends HasHandlers<ObjectType> implements JsonSchemaType {
    private final List<JsonSchemaProperty> properties = new ArrayList<>();

    void addProperty(JsonSchemaProperty property) {
        this.properties.add(property);
    }

    @Override
    public ObjectNode toJsonNode(JsonNodeFactory nodeFactory) {
        ObjectNode node = nodeFactory.objectNode();
        node.put("type", "object");
        List<String> required = getRequiredProperties();
        if (!required.isEmpty()) {
            node.set("required", Utils.toArrayNode(nodeFactory, required));
        }
        ObjectNode propertiesNode = nodeFactory.objectNode();
        node.set("properties", propertiesNode);
        for (JsonSchemaProperty property : properties) {
            propertiesNode.set(property.getName(), property.toJsonNode(nodeFactory));
        }
        handleNode(nodeFactory, node);
        return node;
    }

    private List<String> getRequiredProperties() {
        return properties.stream()
            .filter(JsonSchemaProperty::isRequired)
            .map(JsonSchemaProperty::getName)
            .collect(toList());
    }
}
