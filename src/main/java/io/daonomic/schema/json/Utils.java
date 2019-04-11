package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.*;

public class Utils {
    public static ArrayNode toArrayNode(Collection<String> strings) {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        for (String string : strings) {
            arrayNode.add(string);
        }
        return arrayNode;
    }

    public static List<String> fromArrayNode(ArrayNode arrayNode) {
        ArrayList<String> result = new ArrayList<>(arrayNode.size());
        for (int i = 0; i < arrayNode.size(); i++) {
            result.add(arrayNode.get(i).asText());
        }
        return result;
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... array) {
        return new HashSet<>(Arrays.asList(array));
    }
}
