package io.daonomic.schema.ui;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.daonomic.jackson.mapper.JacksonTypeMapper;

public class UiSchema {
    public static JsonNode generate(JavaType javaType, ObjectMapper objectMapper) throws JsonMappingException {
        return new JacksonTypeMapper<>(new UiSchemaMapper()).map(javaType, objectMapper);
    }
}
