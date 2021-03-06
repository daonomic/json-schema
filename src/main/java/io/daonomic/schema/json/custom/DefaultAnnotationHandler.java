package io.daonomic.schema.json.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

import java.io.IOException;
import java.io.InputStream;

public class DefaultAnnotationHandler implements PropertyAnnotationHandler<Default> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DefaultAnnotationHandler() {
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Default annotation, LabelResolver labels) {
        return property.addHandler(node -> {
            switch (annotation.type()) {
                case STRING:
                    node.put("default", annotation.value());
                    break;
                case JSON:
                    try {
                        node.set("default", objectMapper.readValue(annotation.value(), JsonNode.class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case RESOURCE:
                    try (final InputStream in = getClass().getClassLoader().getResourceAsStream(annotation.value())) {
                        node.set("default", objectMapper.readValue(in, JsonNode.class));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
            }
        });
    }
}
