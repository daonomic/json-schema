package io.daonomic.json.schema.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class IgnoreEmptyDeserializer extends DelegatingDeserializer {
    IgnoreEmptyDeserializer(JsonDeserializer<?> d) {
        super(d);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TreeNode treeNode = p.readValueAsTree();
        if (treeNode instanceof ObjectNode && ((ObjectNode) treeNode).isEmpty(null)) {
            return null;
        }
        return _delegatee.deserialize(p,  ctxt);
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
        return new IgnoreEmptyDeserializer(newDelegatee);
    }
}
