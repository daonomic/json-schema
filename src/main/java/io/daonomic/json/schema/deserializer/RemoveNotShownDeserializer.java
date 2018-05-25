package io.daonomic.json.schema.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.deser.std.DelegatingDeserializer;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.type.SimpleType;
import io.daonomic.json.schema.custom.ShowIf;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class RemoveNotShownDeserializer extends DelegatingDeserializer {
    private final List<ShowIfInfo> properties;

    private RemoveNotShownDeserializer(JsonDeserializer<?> d, List<ShowIfInfo> properties) {
        super(d);
        this.properties = properties;
    }

    static JsonDeserializer<?> create(BeanDescription beanDescription, JsonDeserializer<?> d) {
        Map<String, BeanPropertyDefinition> map = beanDescription.findProperties().stream().collect(toMap(BeanPropertyDefinition::getName, e -> e));
        List<ShowIfInfo> properties = new ArrayList<>();
        map.forEach((name, def) -> {
            if (def.getAccessor() != null) {
                ShowIf ann = def.getAccessor().getAnnotation(ShowIf.class);
                if (ann != null) {
                    BeanPropertyDefinition dependsOn = map.get(ann.field());
                    properties.add(new ShowIfInfo(def, dependsOn, getPositiveValues(dependsOn, ann.value())));
                }
            }
        });
        if (properties.isEmpty()) {
            return d;
        } else {
            return new RemoveNotShownDeserializer(d, properties);
        }
    }

    private static Set<Object> getPositiveValues(BeanPropertyDefinition dependsOn, String[] values) {
        if (dependsOn.getPrimaryType() instanceof SimpleType && dependsOn.getPrimaryType().getRawClass() == boolean.class) {
            return Stream.of(values).map(Boolean::valueOf).collect(Collectors.toSet());
        } else if (dependsOn.getPrimaryType() instanceof SimpleType && dependsOn.getPrimaryType().getRawClass().isEnum()) {
            return Stream.of(values).map(value -> toEnum(value, dependsOn.getPrimaryType().getRawClass())).collect(Collectors.toSet());
        } else {
            throw new IllegalStateException("Only boolean and enums supported, not " + dependsOn.getPrimaryType().getRawClass());
        }
    }

    @SuppressWarnings("unchecked")
    private static Enum toEnum(String value, Class<?> enumClass) {
        return Enum.valueOf((Class<Enum>)enumClass, value);
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
        return new RemoveNotShownDeserializer(newDelegatee, properties);
    }

    @Override
    public Object deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        Object result = super.deserialize(p, ctxt);
        for (ShowIfInfo property : properties) {
            if (!property.values.contains(property.dependsOn.getAccessor().getValue(result))) {
                property.dependant.getAccessor().setValue(result, null);
            }
        }
        return result;
    }

    private static class ShowIfInfo {
        private final BeanPropertyDefinition dependant;
        private final BeanPropertyDefinition dependsOn;
        private final Set<Object> values;

        public ShowIfInfo(BeanPropertyDefinition dependant, BeanPropertyDefinition dependsOn, Set<Object> values) {
            this.dependant = dependant;
            this.dependsOn = dependsOn;
            this.values = values;
        }
    }
}
