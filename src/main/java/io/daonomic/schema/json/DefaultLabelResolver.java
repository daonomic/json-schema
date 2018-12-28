package io.daonomic.schema.json;

public class DefaultLabelResolver implements LabelResolver {
    @Override
    public String resolve(String key) {
        return key;
    }
}
