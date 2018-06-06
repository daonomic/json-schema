package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Utils {
    public static ArrayNode toArrayNode(Collection<String> strings) {
        ArrayNode arrayNode = JsonNodeFactory.instance.arrayNode();
        for (String string : strings) {
            arrayNode.add(string);
        }
        return arrayNode;
    }

    @SafeVarargs
    public static <T> Set<T> asSet(T... array) {
        return new HashSet<>(Arrays.asList(array));
    }
}
