package io.daonomic.json.schema.custom;

import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;
import sun.security.krb5.internal.crypto.Des;

public class DescriptionAnnotationHandler implements PropertyAnnotationHandler<Description> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Description annotation) {
        return property.addHandler(node -> node.put("description", annotation.value()));
    }
}
