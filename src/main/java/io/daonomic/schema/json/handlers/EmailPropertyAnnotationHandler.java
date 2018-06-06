package io.daonomic.schema.json.handlers;

import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import io.daonomic.schema.json.visitors.PrimitiveType;

import javax.validation.constraints.Email;
import java.lang.annotation.Annotation;

public class EmailPropertyAnnotationHandler implements PropertyAnnotationHandler<Email> {

    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Email annotation) {
        if (property.getType() instanceof PrimitiveType
            && ((PrimitiveType) property.getType()).getType() == PrimitiveType.Type.STRING) {
            ((PrimitiveType) property.getType()).setFormat("email");
            return property;
        } else {
            throw new IllegalStateException("Email annotation not supported for property: " + property.getName());
        }
    }
}
