package io.daonomic.json.schema.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;

import java.io.IOException;

public abstract class PreprocessDeserializer extends DelegatingDeserializer {
    public PreprocessDeserializer(JsonDeserializer<?> d) {
        super(d);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        JsonParser newParser = preprocess(p.readValueAsTree()).traverse(p.getCodec());
        newParser.nextToken();
        return _delegatee.deserialize(newParser,  ctxt);
    }

    protected abstract TreeNode preprocess(TreeNode node);
}
