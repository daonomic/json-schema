package io.daonomic.json.schema.visitors.dependencies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.ArrayList;
import java.util.List;

public class FieldDependency implements Dependency {
    private List<String> fields = new ArrayList<>();

    public void addField(String field) {
        fields.add(field);
    }

    @Override
    public JsonNode toJsonNode() {
        if (fields.isEmpty()) {
            return null;
        } else if (fields.size() == 1) {
            return JsonNodeFactory.instance.textNode(fields.get(0));
        } else {
            ArrayNode array = JsonNodeFactory.instance.arrayNode();
            for (String field : fields) {
                array.add(field);
            }
            return array;
        }
    }
}
