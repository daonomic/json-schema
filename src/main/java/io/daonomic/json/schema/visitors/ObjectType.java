package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;
import io.daonomic.json.schema.Utils;
import io.daonomic.json.schema.visitors.dependencies.Dependency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class ObjectType extends HasHandlers<ObjectType> implements JsonSchemaType {
    private final List<JsonSchemaProperty> properties = new ArrayList<>();
    private final Map<String, Dependency> dependencies = new HashMap<>();

    public void addProperty(JsonSchemaProperty property) {
        this.properties.add(property);
    }

    public void addDependency(String field, Function<Dependency, Dependency> changeDependency) {
        Dependency dependency = dependencies.get(field);
        Dependency newDependency = changeDependency.apply(dependency);
        if (newDependency != null) {
            dependencies.put(field, newDependency);
        } else {
            dependencies.remove(field);
        }
    }

    public ObjectType copy() {
        ObjectType copy = new ObjectType();
        for (JsonSchemaProperty property : properties) {
            copy.addProperty(property.copy());
        }
        for (Map.Entry<String, Dependency> entry : dependencies.entrySet()) {
            copy.dependencies.put(entry.getKey(), entry.getValue().copy());
        }
        return copy;
    }

    @Override
    public ObjectNode toJsonSchema() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("type", "object");
        List<String> required = getRequiredProperties();
        if (!required.isEmpty()) {
            node.set("required", Utils.toArrayNode(required));
        }
        ObjectNode propertiesNode = JsonNodeFactory.instance.objectNode();
        node.set("properties", propertiesNode);
        for (JsonSchemaProperty property : properties) {
            propertiesNode.set(property.getName(), property.toJsonNode());
        }
        if (!dependencies.isEmpty()) {
            ObjectNode dependenciesNode = JsonNodeFactory.instance.objectNode();
            node.set("dependencies", dependenciesNode);
            dependencies.forEach((field, dep) -> {
                    JsonNode dependencyNode = dep.toJsonSchema();
                    if (dependencyNode != null)
                        dependenciesNode.set(field, dependencyNode);
                }
            );
        }
        handleNode(node);
        return node;
    }

    @Override
    public ObjectNode toUiSchema() {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        for (JsonSchemaProperty property : properties) {
            result.put(property.getName(), property.getUiSchema());
        }
        return result;
    }

    public List<JsonSchemaProperty> getProperties() {
        return properties;
    }

    public List<String> getRequiredProperties() {
        return properties.stream()
            .filter(JsonSchemaProperty::isRequired)
            .map(JsonSchemaProperty::getName)
            .collect(toList());
    }

}
