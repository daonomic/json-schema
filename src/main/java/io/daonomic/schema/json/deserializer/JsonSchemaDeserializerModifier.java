package io.daonomic.schema.json.deserializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import io.daonomic.schema.json.custom.IgnoreEmpty;

public class JsonSchemaDeserializerModifier extends BeanDeserializerModifier {
    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        deserializer = RemoveNotShownDeserializer.create(beanDesc, deserializer);
        if (beanDesc.getClassAnnotations().has(IgnoreEmpty.class)) {
            return new IgnoreEmptyDeserializer(deserializer);
        } else {
            return deserializer;
        }
    }
}
