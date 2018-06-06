package io.daonomic.schema.json.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;

public class IgnoreEmptyDeserializer extends PreprocessDeserializer {
    IgnoreEmptyDeserializer(JsonDeserializer<?> d) {
        super(d);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        TreeNode treeNode = p.readValueAsTree();
        if (treeNode instanceof ObjectNode && ((ObjectNode) treeNode).isEmpty(null)) {
            return null;
        }
        JsonParser newParser = treeNode.traverse();
        newParser.nextToken();
        return _delegatee.deserialize(newParser,  ctxt);
    }

    @Override
    protected TreeNode preprocess(TreeNode node) {
        if (node instanceof ObjectNode && ((ObjectNode) node).isEmpty(null)) {
            return JsonNodeFactory.instance.nullNode();
        } else {
            return node;
        }
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
        return new IgnoreEmptyDeserializer(newDelegatee);
    }
}
