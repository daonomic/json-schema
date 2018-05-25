package io.daonomic.json.schema.deserializer;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.BeanDeserializerModifier;
import io.daonomic.json.schema.custom.IgnoreEmpty;

public class IgnoreEmptyDeserializerModifier extends BeanDeserializerModifier {
    @Override
    public JsonDeserializer<?> modifyDeserializer(DeserializationConfig config, BeanDescription beanDesc, JsonDeserializer<?> deserializer) {
        IgnoreEmpty ann = beanDesc.getBeanClass().getAnnotation(IgnoreEmpty.class);
        if (ann != null) {
            return new IgnoreEmptyDeserializer(deserializer);
        } else {
            return super.modifyDeserializer(config, beanDesc, deserializer);
        }
    }
}
