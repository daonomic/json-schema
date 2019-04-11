package io.daonomic.schema.json.visitors;

import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitable;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import io.daonomic.schema.json.HandlerFactory;
import io.daonomic.schema.json.JsonSchemaType;

import java.util.Set;

public class ObjectFormatVisitor extends AbstractFormatVisitor<ObjectType> implements JsonObjectFormatVisitor {
    private final HandlerFactory handlerFactory;

    protected ObjectFormatVisitor(ObjectMapper objectMapper, JavaType javaType, HandlerFactory handlerFactory) {
        super(objectMapper, javaType, new ObjectType(null));
        this.handlerFactory = handlerFactory;
    }

    @Override
    public void property(BeanProperty writer) throws JsonMappingException {
        JsonSchemaType propertyType = JsonSchemaVisitor.inspect(writer.getType(), objectMapper, handlerFactory);
        JsonSchemaProperty property = handlerFactory.create(writer).handle(schemaType, new JsonSchemaProperty(writer.getName(), handlerFactory.create(writer.getType()).handle(propertyType), true));
        if (property != null)
            schemaType.addProperty(property);
    }

    @Override
    public void property(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) throws JsonMappingException {
        schemaType.addProperty(new JsonSchemaProperty(name, JsonSchemaVisitor.inspect(propertyTypeHint, objectMapper, handlerFactory), true));
    }

    @Override
    public void optionalProperty(BeanProperty writer) throws JsonMappingException {
        JsonSchemaType propertyType = JsonSchemaVisitor.inspect(writer.getType(), objectMapper, handlerFactory);
        JsonSchemaProperty property = handlerFactory.create(writer).handle(schemaType, new JsonSchemaProperty(writer.getName(), handlerFactory.create(writer.getType()).handle(propertyType), writer.getType().isPrimitive() && writer.getType().getRawClass() != boolean.class));
        if (property != null)
            schemaType.addProperty(property);
    }

    @Override
    public void optionalProperty(String name, JsonFormatVisitable handler, JavaType propertyTypeHint) throws JsonMappingException {
        schemaType.addProperty(new JsonSchemaProperty(name, JsonSchemaVisitor.inspect(propertyTypeHint, objectMapper, handlerFactory), propertyTypeHint.isPrimitive()));
    }

    @Override
    public void format(JsonValueFormat format) {

    }

    @Override
    public void enumTypes(Set<String> enums) {

    }
}
