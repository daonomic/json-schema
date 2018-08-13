package io.daonomic.schema.ui.handlers;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.ui.annotations.UiInline;

public class UiInlineHandler implements UiSchemaPropertyHandler<UiInline>, UiSchemaTypeHandler<UiInline> {
    @Override
    public void handle(UiInline annotation, ObjectNode node) {
        node.put("ui:inline", true);
    }
}
