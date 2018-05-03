package io.daonomic.json.schema.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;
import io.daonomic.json.schema.Utils;
import io.daonomic.json.schema.annotations.Order;
import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.ObjectType;
import io.daonomic.json.schema.visitors.OneOfType;
import io.daonomic.json.schema.visitors.dependencies.Dependency;
import io.daonomic.json.schema.visitors.dependencies.SchemaDependency;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import static io.daonomic.json.schema.Utils.asSet;

@Order(Integer.MAX_VALUE)
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
        ObjectType positive = findEnumWithValues(oneOfType, annotation.field(), asSet(annotation.positive()));
        if (positive == null) {
            oneOfType.addType(createPositive(property, annotation));
        } else {
            if (annotation.required()) {
                positive.addProperty(property.required(true));
            } else {
                positive.addProperty(property);
            }
        }
        ObjectType negative = findEnumWithValues(oneOfType, annotation.field(), asSet(annotation.negative()));
        if (negative == null) {
            oneOfType.addType(createNegative(annotation));
        }
        return new SchemaDependency(oneOfType);
    }

    private ObjectType findEnumWithValues(OneOfType type, String fieldName, Set<String> enums) {
        for (JsonSchemaType oneOfItem : type.getTypes()) {
            if (oneOfItem instanceof ObjectType) {
                for (JsonSchemaProperty property : ((ObjectType) oneOfItem).getProperties()) {
                    if (property.getName().equals(fieldName) && property.getType() instanceof EnumSchemaType && ((EnumSchemaType) property.getType()).getValues().equals(enums)) {
                        return (ObjectType) oneOfItem;
                    }
                }
            }
        }
        return null;
    }

    private ObjectType createPositive(JsonSchemaProperty property, ShowIf annotation) {
        ObjectType result = new ObjectType();
        result.addProperty(new JsonSchemaProperty(annotation.field(), new EnumSchemaType(asSet(annotation.positive())), false));
        if (annotation.required()) {
            result.addProperty(property.required(true));
        } else {
            result.addProperty(property);
        }
        return result;
    }

    private JsonSchemaType createNegative(ShowIf annotation) {
        ObjectType result = new ObjectType();
        result.addProperty(new JsonSchemaProperty(annotation.field(), new EnumSchemaType(asSet(annotation.negative())), false));
        return result;
    }

    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, ShowIf annotation) {
        return null;
    }

    private class EnumSchemaType implements JsonSchemaType {
        private final Set<String> values;

        private EnumSchemaType(Set<String> values) {
            this.values = values;
        }

        private Set<String> getValues() {
            return values;
        }

        @Override
        public ObjectNode toJsonNode() {
            ObjectNode result = JsonNodeFactory.instance.objectNode();
            result.set("enum", getValue(values));
            return result;
        }

        private ArrayNode getValue(Collection<String> value) {
            ArrayNode result = JsonNodeFactory.instance.arrayNode();
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
