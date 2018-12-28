package io.daonomic.schema.json.custom;

import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.Utils;
import io.daonomic.schema.json.annotations.PropertyAnnotationHandler;
import io.daonomic.schema.json.visitors.JsonSchemaProperty;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CustomEnumAnnotationHandler implements PropertyAnnotationHandler<CustomEnum> {
    @Override
    public JsonSchemaProperty handle(JsonSchemaProperty property, CustomEnum annotation, LabelResolver labels) {
        return property.addHandler(node -> {
            List<String> names = Stream.of(annotation.value())
                .map(labels::resolve)
                .collect(Collectors.toList());

            node.set("enum", Utils.toArrayNode(Arrays.asList(annotation.value())));
            node.set("enumNames", Utils.toArrayNode(names));
        });
    }
}
