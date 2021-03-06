package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.JsonNodeHandler;

import java.util.ArrayList;
import java.util.List;

public class HasHandlers<T extends HasHandlers<T>> {
    private List<JsonNodeHandler> handlers = new ArrayList<>();

    protected void handleNode(ObjectNode node) {
        for (JsonNodeHandler handler : handlers) {
            handler.handle(node);
        }
    }

    @SuppressWarnings("unchecked")
    public T addHandler(JsonNodeHandler handler) {
        this.handlers.add(handler);
        return (T) this;
    }
}
