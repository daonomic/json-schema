package io.daonomic.jackson.visitor;

import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWithSerializerProvider;

public class AbstractVisitor implements JsonFormatVisitorWithSerializerProvider {
    private SerializerProvider provider;

    @Override
    public SerializerProvider getProvider() {
        return provider;
    }

    @Override
    public void setProvider(SerializerProvider provider) {
        this.provider = provider;
    }
}
