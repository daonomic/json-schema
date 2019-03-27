package io.daonomic.schema.ui.handlers;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.schema.json.LabelResolver;
import io.daonomic.schema.json.Utils;
import io.daonomic.schema.ui.annotations.EnumDescriptions;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class EnumDescriptionsHandler implements UiSchemaTypeHandler<EnumDescriptions, Enum> {
    @Override
    public void handle(Class<Enum> rawClass, EnumDescriptions annotation, ObjectNode node, LabelResolver labels) {
        final ObjectNode options;
        if (node.path("options").isObject()) {
            options = (ObjectNode) node.path("options");
        } else {
            options = JsonNodeFactory.instance.objectNode();
            node.set("options", options);
        }
        List<String> descriptions = Stream.of(rawClass.getEnumConstants())
            .map(Enum::name)
            .map(name -> labels.resolve(annotation.value() + "." + name))
            .collect(toList());
        options.set("descriptions", Utils.toArrayNode(descriptions));
    }
}
