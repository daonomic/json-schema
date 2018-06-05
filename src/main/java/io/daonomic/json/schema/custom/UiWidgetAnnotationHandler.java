package io.daonomic.json.schema.custom;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.annotations.PropertyAnnotationHandler;
import io.daonomic.json.schema.visitors.JsonSchemaProperty;

public class UiWidgetAnnotationHandler implements PropertyAnnotationHandler<UiWidget> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, UiWidget annotation) {
        property.getUiSchema().put("ui:widget", annotation.value());
        if (annotation.options().length != 0) {
            ObjectNode options = JsonNodeFactory.instance.objectNode();
            for (UiWidget.Option option : annotation.options()) {
                options.put(option.key(), option.value());
            }
            property.getUiSchema().put("ui:options", options);
        }
        return property;
    }
}
