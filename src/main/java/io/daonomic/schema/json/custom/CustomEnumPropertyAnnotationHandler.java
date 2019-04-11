package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.Utils;
import io.daonomic.schema.json.annotations.Order;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

@Order(-1000)
public class CustomEnumPropertyAnnotationHandler implements PropertyAnnotationHandler<CustomEnumProperty> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, CustomEnumProperty annotation, LabelResolver labels) {
        return property.addHandler(node -> {
            node.set("enum", Utils.toArrayNode(Arrays.asList(annotation.value())));
            String prefix = annotation.labelPrefix().isEmpty() ? "" : (annotation.labelPrefix() + ".");
            node.set("enumNames", Utils.toArrayNode(Stream.of(annotation.value()).map(name -> labels.resolve(prefix + name)).collect(toList())));
        });
    }
}
