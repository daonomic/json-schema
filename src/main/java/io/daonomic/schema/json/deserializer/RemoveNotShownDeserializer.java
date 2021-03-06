package io.daonomic.schema.json.deserializer;

import com.fasterxml.jackson.core.TreeNode;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.SimpleType;
import io.daonomic.schema.json.custom.ShowIf;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class RemoveNotShownDeserializer extends PreprocessDeserializer {
    private final List<ShowIfInfo> properties;

    private RemoveNotShownDeserializer(JsonDeserializer<?> d, List<ShowIfInfo> properties) {
        super(d);
        this.properties = properties;
    }

    static JsonDeserializer<?> create(BeanDescription beanDescription, JsonDeserializer<?> d) {
        Map<String, BeanPropertyDefinition> map = beanDescription.findProperties().stream().collect(toMap(BeanPropertyDefinition::getName, e -> e));
        List<ShowIfInfo> properties = new ArrayList<>();
        map.forEach((name, def) -> {
            ShowIf ann = getAnnotation(def, ShowIf.class);
            if (ann != null) {
                BeanPropertyDefinition dependsOn = map.get(ann.field());
                properties.add(new ShowIfInfo(def.getName(), dependsOn.getName(), getPositiveValues(dependsOn, ann.value())));
            }
        });
        if (properties.isEmpty()) {
            return d;
        } else {
            return new RemoveNotShownDeserializer(d, properties);
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static <A extends Annotation> A getAnnotation(BeanPropertyDefinition def, Class<A> aClass) {
        A fieldAnnotation = def.getField() != null ? def.getField().getAnnotation(aClass) : null;
        if (fieldAnnotation != null) {
            return fieldAnnotation;
        } else {
            return def.getAccessor() != null ? def.getAccessor().getAnnotation(aClass) : null;
        }
    }

    private static Set<Object> getPositiveValues(BeanPropertyDefinition dependsOn, String[] values) {
        if (dependsOn.getPrimaryType() instanceof SimpleType && dependsOn.getPrimaryType().getRawClass() == boolean.class) {
            return Stream.of(values).map(Boolean::valueOf).map(JsonNodeFactory.instance::booleanNode).collect(Collectors.toSet());
        } else {
            return Stream.of(values).map(JsonNodeFactory.instance::textNode).collect(Collectors.toSet());
        }
    }

    @Override
    protected JsonDeserializer<?> newDelegatingInstance(JsonDeserializer<?> newDelegatee) {
        return new RemoveNotShownDeserializer(newDelegatee, properties);
    }

    @Override
    protected TreeNode preprocess(TreeNode node) {
        if (node instanceof ObjectNode) {
            ObjectNode object = (ObjectNode) node;
            for (ShowIfInfo property : properties) {
                if (!property.values.contains(object.get(property.dependsOn))) {
                    object.remove(property.dependant);
                }
            }
        }
        return node;
    }

    private static class ShowIfInfo {
        private final String dependant;
        private final String dependsOn;
        private final Set<Object> values;

        ShowIfInfo(String dependant, String dependsOn, Set<Object> values) {
            this.dependant = dependant;
            this.dependsOn = dependsOn;
            this.values = values;
        }
    }
}
