package io.daonomic.json.schema.visitors;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.json.schema.PropertyHandlerFactory;

import java.util.Set;

public class ObjectFormatVisitor extends AbstractFormatVisitor<ObjectType> implements JsonObjectFormatVisitor {
    private final PropertyHandlerFactory propertyHandlerFactory;

    protected ObjectFormatVisitor(ObjectMapper objectMapper, PropertyHandlerFactory propertyHandlerFactory) {
        super(objectMapper, new ObjectType());
        this.propertyHandlerFactory = propertyHandlerFactory;
    }

    @Override
    public void property(BeanProperty writer) throws JsonMappingException {
        JsonSchemaProperty property = propertyHandlerFactory.create(writer).handle(schemaType, new JsonSchemaProperty(writer.getName(), JsonFormatVisitor.inspect(writer.getType(), objectMapper, propertyHandlerFactory), true));
        if (property != null)
            schemaType.addProperty(property);
    }

    @Override
    public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) throws JsonMappingException {
        schemaType.addProperty(new JsonSchemaProperty(name, JsonFormatVisitor.inspect(propertyTypeHint, objectMapper, propertyHandlerFactory), true));
    }

    @Override
    public void optionalProperty(BeanProperty writer) throws JsonMappingException {
        JsonSchemaProperty property = propertyHandlerFactory.create(writer).handle(schemaType, new JsonSchemaProperty(writer.getName(), JsonFormatVisitor.inspect(writer.getType(), objectMapper, propertyHandlerFactory), writer.getType().isPrimitive() && writer.getType().getRawClass() != boolean.class));
        if (property != null)
            schemaType.addProperty(property);
    }

    @Override
    public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) throws JsonMappingException {
        schemaType.addProperty(new JsonSchemaProperty(name, JsonFormatVisitor.inspect(propertyTypeHint, objectMapper, propertyHandlerFactory), propertyTypeHint.isPrimitive()));
    }

    @Override
    public void format(JsonValueFormat format) {

    }

    @Override
    public void enumTypes(Set<String> enums) {

    }
}
