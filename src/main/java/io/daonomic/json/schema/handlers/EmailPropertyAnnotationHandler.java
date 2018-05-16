package io.daonomic.json.schema.handlers;

import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import io.daonomic.json.schema.visitors.PrimitiveType;

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
