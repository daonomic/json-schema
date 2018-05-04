package io.daonomic.json.schema.custom;

import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.ArrayType;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;

public class ItemTitleAnnotationHandler implements PropertyAnnotationHandler<ItemTitle> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, ItemTitle annotation) {
        if (property.getType() instanceof ArrayType) {
            ((ArrayType) property.getType()).addHandler(node -> node.put("title", annotation.value()));
            return property;
        } else {
            throw new IllegalStateException("Only supported for array type");
        }
    }
}
