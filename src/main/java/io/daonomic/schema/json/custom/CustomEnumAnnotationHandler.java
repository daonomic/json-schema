package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.Utils;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

import java.util.Arrays;

public class CustomEnumAnnotationHandler implements PropertyAnnotationHandler<CustomEnum> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, CustomEnum annotation) {
        return property.addHandler(node -> {
            node.set("enum", Utils.toArrayNode(Arrays.asList(annotation.value())));
        });
    }
}
