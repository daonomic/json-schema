package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;
import sun.security.krb5.internal.crypto.Des;

public class DescriptionAnnotationHandler implements PropertyAnnotationHandler<Description> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Description annotation, LabelResolver labels) {
        return property.addHandler(node -> node.put("description", labels.resolve(annotation.value())));
    }
}
