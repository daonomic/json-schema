package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.annotations.Order;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.ObjectType;
import io.daonomic.schema.json.visitors.dependencies.FieldDependency;

import java.util.List;

@Order(Integer.MAX_VALUE - 1000)
public class IgnoreEmptyAnnotationHandler implements PropertyAnnotationHandler<IgnoreEmpty> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, IgnoreEmpty annotation, LabelResolver labels) {
        if (property.isRequired()) {
            return property;
        }
        if (property.getType() instanceof ObjectType) {
            ObjectType type = (ObjectType) property.getType();

            for (JsonSchemaProperty childProperty : type.getProperties()) {
                type.addDependency(childProperty.getName(), dep -> {
                    if (dep != null) {
                        throw new IllegalStateException("dependencies for field " + childProperty.getName() + " should be empty");
                    } else {
                        List<String> required = type.getRequiredProperties();
                        required.remove(childProperty.getName());
                        return new FieldDependency(required);
                    }
                });
            }
            for (JsonSchemaProperty childProperty : type.getProperties()) {
                childProperty.required(false);
            }
            return property;
        } else {
            throw new IllegalStateException("Only object schemas supported");
        }
    }
}
