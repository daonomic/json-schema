package io.daonomic.json.schema.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;
import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.NotType;
import io.daonomic.json.schema.visitors.ObjectType;
import io.daonomic.json.schema.visitors.OneOfType;
import io.daonomic.json.schema.visitors.dependencies.Dependency;
import io.daonomic.json.schema.visitors.dependencies.SchemaDependency;

import java.io.IOException;

public class ShowIfAnnotationHandler implements PropertyAnnotationHandler<ShowIf> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShowIfAnnotationHandler() {
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Override
    public JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property, ShowIf annotation) {
        objectType.addDependency(annotation.field(), existing -> {
            if (existing == null) {
                return changeDependency(new OneOfType(), property, annotation);
            } else if (existing instanceof SchemaDependency) {
                JsonSchemaType schemaType = ((SchemaDependency) existing).getSchemaType();
                if (schemaType instanceof OneOfType) {
                    return changeDependency((OneOfType) schemaType, property, annotation);
                } else {
                    throw new IllegalStateException("Only oneOf dependency is allowed");
                }
            } else {
                throw new IllegalStateException("Only schema dependency is allowed");
            }
        });
        return null;
    }

    private Dependency changeDependency(OneOfType oneOfType, JsonSchemaProperty property, ShowIf annotation) {
        oneOfType.addType(createPositive(property, annotation));
        oneOfType.addType(createNegative(property, annotation));
        return new SchemaDependency(oneOfType);
    }

    private JsonSchemaType createPositive(JsonSchemaProperty property, ShowIf annotation) {
        ObjectType result = new ObjectType();
        result.addProperty(new JsonSchemaProperty(annotation.field(), new EnumSchemaType(annotation.positive()), false));
        if (annotation.required()) {
            result.addProperty(property.required(true));
        } else {
            result.addProperty(property);
        }
        return result;
    }

    private JsonSchemaType createNegative(JsonSchemaProperty property, ShowIf annotation) {
        if (annotation.negative().length == 0) {
            ObjectType notType = new ObjectType();
            notType.addProperty(new JsonSchemaProperty(annotation.field(), new EnumSchemaType(annotation.positive()), false));
            return new NotType(notType);
        } else {
            ObjectType result = new ObjectType();
            result.addProperty(new JsonSchemaProperty(annotation.field(), new EnumSchemaType(annotation.negative()), false));
            return result;
        }
    }

    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, ShowIf annotation) {
        return null;
    }

    private class EnumSchemaType implements JsonSchemaType {
        private final String[] values;

        private EnumSchemaType(String[] values) {
            this.values = values;
        }

        @Override
        public ObjectNode toJsonNode(JsonNodeFactory factory) {
            ObjectNode result = factory.objectNode();
            result.set("enum", getValue(factory, values));
            return result;
        }

        private ArrayNode getValue(JsonNodeFactory factory, String[] value) {
            ArrayNode result = factory.arrayNode();
            for (String s : value) {
                try {
                    result.add(objectMapper.readValue(s, JsonNode.class));
                } catch (IOException e) {
                    result.add(s);
                }
            }
            return result;
        }
    }

}
