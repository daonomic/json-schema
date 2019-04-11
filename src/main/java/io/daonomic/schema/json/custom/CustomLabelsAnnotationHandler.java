package io.daonomic.schema.json.custom;

import com.fasterxml.jackson.databind.node.ArrayNode;
import io.daonomic.schema.json.JsonSchemaType;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.Utils;
import io.daonomic.schema.json.annotations.TypeAnnotationHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class CustomLabelsAnnotationHandler implements TypeAnnotationHandler<CustomLabels> {
    @Override
    public JsonSchemaType handle(JsonSchemaType jsonSchemaType, CustomLabels annotation, LabelResolver labels) {
        return (JsonSchemaType) jsonSchemaType.addHandler(node -> {
            if (jsonSchemaType.getJavaType() != null) {
                List<String> enums = Utils.fromArrayNode(((ArrayNode) node.get("enum")));
                final String prefix;
                if (annotation.value().equals("DEFAULT")) {
                    prefix = jsonSchemaType.getJavaType().getRawClass().getSimpleName() + ".";
                } else if (annotation.value().equals("")) {
                    prefix = "";
                } else {
                    prefix = annotation.value() + ".";
                }
                node.set("enumNames", Utils.toArrayNode(enums.stream().map(name -> labels.resolve(prefix + name)).collect(toList())));
            }
        });
    }
}
