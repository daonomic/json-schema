package io.daonomic.schema.json.custom;

import com.fasterxml.jackson.databind.node.ArrayNode;
import io.daonomic.schema.json.JsonSchemaType;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.Utils;
import io.daonomic.schema.json.annotations.TypeAnnotationHandler;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class EnumDescriptionsAnnotationHandler implements TypeAnnotationHandler<EnumDescriptions> {
    @Override
    public JsonSchemaType handle(JsonSchemaType jsonSchemaType, EnumDescriptions annotation, LabelResolver labels) {
        return (JsonSchemaType) jsonSchemaType.addHandler(node -> {
            List<String> enums = Utils.fromArrayNode(((ArrayNode) node.get("enum")));
            String prefix = annotation.value() + ".";
            node.set("enumDescriptions", Utils.toArrayNode(enums.stream().map(name -> labels.resolve(prefix + name)).collect(toList())));
        });
    }
}
