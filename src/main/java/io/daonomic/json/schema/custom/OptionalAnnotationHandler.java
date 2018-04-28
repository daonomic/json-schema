package io.daonomic.json.schema.custom;

import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.ObjectType;
import io.daonomic.json.schema.visitors.dependencies.FieldDependency;

import java.util.List;

public class OptionalAnnotationHandler implements PropertyAnnotationHandler<Optional> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Optional annotation) {
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
