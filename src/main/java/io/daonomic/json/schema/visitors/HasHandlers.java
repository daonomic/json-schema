package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.json.schema.JsonNodeHandler;

import java.util.ArrayList;
import java.util.List;

public class HasHandlers<T extends HasHandlers<T>> {
    private List<JsonNodeHandler> handlers = new ArrayList<>();

    protected void handleNode(JsonNodeFactory nodeFactory, ObjectNode node) {
        for (JsonNodeHandler handler : handlers) {
            handler.handle(nodeFactory, node);
        }
    }

    @SuppressWarnings("unchecked")
    public T addHandler(JsonNodeHandler handler) {
        this.handlers.add(handler);
        return (T) this;
    }
}
