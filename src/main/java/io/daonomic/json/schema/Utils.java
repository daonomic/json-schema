package io.daonomic.json.schema;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.List;

public class Utils {
    public static ArrayNode toArrayNode(JsonNodeFactory nodeFactory, List<String> strings) {
        ArrayNode arrayNode = nodeFactory.arrayNode();
        for (String string : strings) {
            arrayNode.add(string);
        }
        return arrayNode;
    }
}
