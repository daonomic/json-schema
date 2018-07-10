package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.PrimitiveType;

public class FormatAnnotationHandler implements PropertyAnnotationHandler<Format> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Format annotation) {
        if (property.getType() instanceof PrimitiveType) {
            ((PrimitiveType) property.getType()).setFormat(annotation.value());
            return property;
        } else {
            throw new IllegalStateException("Unsupported property type: " + property.getType());
        }
    }
}
