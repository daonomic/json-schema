package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

public class TitleAnnotationHandler implements PropertyAnnotationHandler<Title> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, Title annotation, LabelResolver labels) {
        return property.addHandler(node -> node.put("title", labels.resolve(annotation.value())));
    }
}
