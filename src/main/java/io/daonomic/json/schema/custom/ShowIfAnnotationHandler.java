package io.daonomic.json.schema.custom;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonSchemaType;
import io.daonomic.json.schema.annotations.Order;
import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.*;
import io.daonomic.json.schema.visitors.dependencies.Dependency;
import io.daonomic.json.schema.visitors.dependencies.SchemaDependency;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static io.daonomic.json.schema.Utils.asSet;
import static java.util.Arrays.asList;

@Order(Integer.MAX_VALUE - 1000000)
public class ShowIfAnnotationHandler implements PropertyAnnotationHandler<ShowIf> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ShowIfAnnotationHandler() {
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    }

    @Override
    public JsonSchemaProperty handle(ObjectType objectType, JsonSchemaProperty property, ShowIf annotation) {
        objectType.addDependency(annotation.field(), existing -> {
            if (existing == null) {
                return changeDependency(objectType, new OneOfType(), property, annotation);
            } else if (existing instanceof SchemaDependency) {
                JsonSchemaType schemaType = ((SchemaDependency) existing).getSchemaType();
                if (schemaType instanceof OneOfType) {
                    return changeDependency(objectType, (OneOfType) schemaType, property, annotation);
                } else {
                    throw new IllegalStateException("Only oneOf dependency is allowed");
                }
            } else {
                throw new IllegalStateException("Only schema dependency is allowed");
            }
        });
        return null;
    }

    private Dependency changeDependency(ObjectType objectType, OneOfType oneOfType, JsonSchemaProperty property, ShowIf annotation) {
        ObjectType positive = findEnumWithValues(oneOfType, annotation.field(), asSet(annotation.value()));
        if (positive == null) {
            oneOfType.addType(createPositive(property, annotation));
        } else {
            if (annotation.required()) {
                positive.addProperty(property.required(true));
            } else {
                positive.addProperty(property);
            }
        }
        Set<String> negativeValues = getNegativeValues(objectType, annotation);
        if (negativeValues.isEmpty()) {
            oneOfType.addType(createNotPositive(annotation.field(), asSet(annotation.value())));
        } else {
            Set<String> notFoundNegativeValues = new HashSet<>();
            for (String negativeValue : negativeValues) {
                if (!hasEnumWithValue(oneOfType, annotation.field(), negativeValue)) {
                    notFoundNegativeValues.add(negativeValue);
                }
            }
            if (!notFoundNegativeValues.isEmpty()) {
                oneOfType.addType(createNegative(annotation.field(), notFoundNegativeValues));
            }
        }
        return new SchemaDependency(oneOfType);
    }

    private Set<String> getNegativeValues(ObjectType objectType, ShowIf annotation) {
        Optional<JsonSchemaProperty> dependsOnOpt = objectType.getProperties().stream().filter(p -> p.getName().equals(annotation.field())).findAny();
        if (!dependsOnOpt.isPresent()) {
            throw new IllegalStateException("Schema doesn't contain " + annotation.field() + " property");
        }
        JsonSchemaProperty dependsOn = dependsOnOpt.get();
        final Set<String> enums;
        if (dependsOn.getType() instanceof PrimitiveType) {
            switch (((PrimitiveType) dependsOn.getType()).getType()) {
                case BOOLEAN:
                    enums = asSet("true", "false");
                    break;
                case STRING:
                    if (((PrimitiveType) dependsOn.getType()).getEnums() != null) {
                        enums = new HashSet<>(((PrimitiveType) dependsOn.getType()).getEnums());
                    } else {
                        enums = new HashSet<>();
                    }
                    break;
                default:
                    enums = new HashSet<>();
            }
        } else {
            throw new IllegalArgumentException("Only primitive types supported");
        }
        enums.removeAll(asList(annotation.value()));
        return enums;
    }

    private boolean hasEnumWithValue(OneOfType type, String fieldName, String enumValue) {
        for (JsonSchemaType oneOfItem : type.getTypes()) {
            if (oneOfItem instanceof ObjectType) {
                for (JsonSchemaProperty property : ((ObjectType) oneOfItem).getProperties()) {
                    if (property.getName().equals(fieldName) && property.getType() instanceof EnumSchemaType) {
                        EnumSchemaType enumType = (EnumSchemaType) property.getType();
                        if (enumType.getValues().contains(enumValue)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private ObjectType findEnumWithValues(OneOfType type, String fieldName, Set<String> enums) {
        for (JsonSchemaType oneOfItem : type.getTypes()) {
            if (oneOfItem instanceof ObjectType) {
                for (JsonSchemaProperty property : ((ObjectType) oneOfItem).getProperties()) {
                    if (property.getName().equals(fieldName) && property.getType() instanceof EnumSchemaType) {
                        EnumSchemaType enumType = (EnumSchemaType) property.getType();
                        if (enumType.getValues().equals(enums)) {
                            return (ObjectType) oneOfItem;
                        } else if (enumType.getValues().containsAll(enums)) {
                            ObjectType copy = ((ObjectType) oneOfItem).copy();
                            HashSet<String> notEnums = new HashSet<>(enumType.getValues());
                            notEnums.removeAll(enums);
                            setEnumValues(copy, fieldName, notEnums);
                            type.addType(copy);
                            setEnumValues((ObjectType) oneOfItem, fieldName, enums);
                            return (ObjectType) oneOfItem;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void setEnumValues(ObjectType objectType, String propertyName, Set<String> values) {
        for (JsonSchemaProperty property : objectType.getProperties()) {
            if (property.getName().equals(propertyName) && property.getType() instanceof EnumSchemaType) {
                ((EnumSchemaType) property.getType()).setValues(values);
            }
        }
    }

    private ObjectType createPositive(JsonSchemaProperty property, ShowIf annotation) {
        ObjectType result = new ObjectType();
        result.addProperty(new JsonSchemaProperty(annotation.field(), new EnumSchemaType(asSet(annotation.value())), false));
        if (annotation.required()) {
            result.addProperty(property.required(true));
        } else {
            result.addProperty(property);
        }
        return result;
    }

    private ObjectType createNegative(String dependsOn, Set<String> negative) {
        ObjectType result = new ObjectType();
        result.addProperty(new JsonSchemaProperty(dependsOn, new EnumSchemaType(negative), false));
        return result;
    }

    private JsonSchemaType createNotPositive(String dependsOn, Set<String> positive) {
        ObjectType result = new ObjectType();
        result.addProperty(new JsonSchemaProperty(dependsOn, new NotType(new EnumSchemaType(positive)), false));
        return result;
    }

    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, ShowIf annotation) {
        return null;
    }

    private class EnumSchemaType implements JsonSchemaType<EnumSchemaType> {
        private Set<String> values;

        private EnumSchemaType(Set<String> values) {
            this.values = values;
        }

        private Set<String> getValues() {
            return values;
        }

        private void setValues(Set<String> values) {
            this.values = values;
        }

        @Override
        public EnumSchemaType copy() {
            return new EnumSchemaType(new HashSet<>(values));
        }

        @Override
        public ObjectNode toJsonSchema() {
            ObjectNode result = JsonNodeFactory.instance.objectNode();
            result.set("enum", getValue(values));
            return result;
        }

        @Override
        public ObjectNode toUiSchema() {
            return null;
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
