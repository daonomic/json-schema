package io.daonomic.schema.json.custom;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

public class UniqueItemsAnnotationHandler implements PropertyAnnotationHandler<UniqueItems> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, UniqueItems annotation, LabelResolver labels) {
        return property.addHandler(node -> node.set("uniqueItems", JsonNodeFactory.instance.booleanNode(true)));
    }
}
