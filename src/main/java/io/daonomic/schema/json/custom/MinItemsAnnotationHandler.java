package io.daonomic.schema.json.custom;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

public class MinItemsAnnotationHandler implements PropertyAnnotationHandler<MinItems> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, MinItems annotation, LabelResolver labels) {
        return property.addHandler(node -> node.set("minItems", JsonNodeFactory.instance.numberNode(annotation.value())));
    }
}
