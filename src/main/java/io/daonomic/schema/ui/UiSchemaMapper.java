package io.daonomic.schema.ui;

import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.daonomic.jackson.domain.*;
import io.daonomic.jackson.mapper.JacksonMapper;
import io.daonomic.jackson.mapper.JacksonTypeMapper;
import io.daonomic.schema.json.InstanceCache;
import io.daonomic.schema.ui.handlers.UiSchemaPropertyHandler;

import java.lang.annotation.Annotation;

public class UiSchemaMapper implements JacksonMapper<ObjectNode, ObjectNode, ObjectNode, ObjectNode, ObjectNode, ObjectNode> {
    @Override
    public ObjectNode fromObject(JacksonObjectType object, JacksonTypeMapper<ObjectNode, ObjectNode, ObjectNode, ObjectNode, ObjectNode, ObjectNode> mapper) {
        ObjectNode result = JsonNodeFactory.instance.objectNode();
        for (JacksonProperty property : object.getProperties()) {
            ObjectNode node = handleProperty(property, mapper.mapJacksonType(property.getType()));
            if (node != null) {
                result.set(property.getName(), node);
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    private ObjectNode handleProperty(JacksonProperty property, ObjectNode objectNode) {
        for (Annotation annotation : property.getBeanProperty().getMember().getAllAnnotations().annotations()) {
            Class<? extends UiSchemaPropertyHandler> handlerClass = UiSchemaPropertyHandler.getByAnnotation(annotation);
            if (handlerClass != null) {
                if (objectNode == null) {
                    objectNode = JsonNodeFactory.instance.objectNode();
                }
                InstanceCache.INSTANCE.get(handlerClass).handle(annotation, objectNode);
            }
        }
        return objectNode;
    }

    @Override
    public ObjectNode fromArray(JacksonArrayType array, JacksonTypeMapper<ObjectNode, ObjectNode, ObjectNode, ObjectNode, ObjectNode, ObjectNode> mapper) {
        ObjectNode mappedType = mapper.mapJacksonType(array.getItemType());
        if (mappedType != null && !mappedType.isEmpty(null)) {
            ObjectNode result = JsonNodeFactory.instance.objectNode();
            result.set("items", mappedType);
            return result;
        } else {
            return null;
        }
    }

    @Override
    public ObjectNode fromNumber(JacksonNumberType number) {
        return null;
    }

    @Override
    public ObjectNode fromPrimitive(JacksonPrimitiveType primitive) {
        return null;
    }

    @Override
    public ObjectNode fromMap(JacksonMapType map) {
        return null;
    }
}
