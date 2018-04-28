package io.daonomic.json.schema.visitors.dependencies;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import java.util.List;

public class FieldDependency implements Dependency {
    private List<String> fields;

    public FieldDependency(List<String> fields) {
        this.fields = fields;
    }

    public void addField(String field) {
        fields.add(field);
    }

    @Override
    public JsonNode toJsonNode() {
        if (fields.isEmpty()) {
            return null;
        } else {
            ArrayNode array = JsonNodeFactory.instance.arrayNode();
            for (String field : fields) {
                array.add(field);
            }
            return array;
        }
    }
}
