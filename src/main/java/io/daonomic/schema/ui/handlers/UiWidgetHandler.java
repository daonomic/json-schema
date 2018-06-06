package io.daonomic.schema.ui.handlers;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.ui.annotations.UiWidget;

public class UiWidgetHandler implements UiSchemaPropertyHandler<UiWidget> {
    @Override
    public void handle(UiWidget annotation, ObjectNode node) {
        node.put("ui:widget", annotation.value());
        if (annotation.options().length != 0) {
            ObjectNode options = JsonNodeFactory.instance.objectNode();
            for (UiWidget.Option option : annotation.options()) {
                options.put(option.key(), option.value());
            }
            node.set("ui:options", options);
        }
    }
}
