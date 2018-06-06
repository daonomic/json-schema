package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.ArrayType;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

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
