package io.daonomic.json.schema.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.Utils;
import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.ObjectType;

import java.io.IOException;
import java.util.Arrays;

public class ShowIfAnnotationHandler implements PropertyAnnotationHandler<ShowIf> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShowIfAnnotationHandler() {
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Override
    public JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property, ShowIf annotation) {
        objectType.addHandler((factory, node) -> {
            JsonNode value = getValue(factory, annotation);

            ObjectNode dependency = factory.objectNode();
            ArrayNode array = factory.arrayNode();
            ObjectNode positive = factory.objectNode();
            if (annotation.required()) {
                positive.set("required", Utils.toArrayNode(factory, Arrays.asList(property.getName())));
            }
            ObjectNode positiveProperties = factory.objectNode();
            positiveProperties.set(annotation.field(), factory.objectNode().set("enum", value));
            positiveProperties.set(property.getName(), property.toJsonNode(factory));
            positive.set("properties", positiveProperties);
            array.add(positive);
            ObjectNode negative = factory.objectNode();
            ObjectNode negativeProperties = factory.objectNode();
            negativeProperties.set(annotation.field(), factory.objectNode().set("not", factory.objectNode().set("enum", value)));
            negative.set("properties", negativeProperties);
            array.add(negative);
            dependency.set("oneOf", array);
            node.set("dependencies", factory.objectNode().set(annotation.field(), dependency));
        });
        return null;
    }

    private JsonNode getValue(JsonNodeFactory nodeFactory, ShowIf annotation) {
        try {
            JsonNode value = objectMapper.readValue(annotation.value(), JsonNode.class);
            if (value.isArray()) {
                return value;
            } else {
                return nodeFactory.arrayNode().add(value);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, ShowIf annotation) {
        return null;
    }
}
