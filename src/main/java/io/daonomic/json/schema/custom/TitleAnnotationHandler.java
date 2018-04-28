package io.daonomic.json.schema.custom;

import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;

public class TitleAnnotationHandler implements PropertyAnnotationHandler<Title> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Title annotation) {
        return property.addHandler(node -> node.put("title", annotation.value()));
    }
}
