package io.daonomic.jackson.domain;

import com.fasterxml.jackson.databind.JavaType;

import java.util.ArrayList;
import java.util.List;

public class JacksonObjectType implements JacksonType {
    private final JavaType javaType;
    private List<JacksonProperty> properties = new ArrayList<>();

    public JacksonObjectType(JavaType javaType) {
        this.javaType = javaType;
    }

    public List<JacksonProperty> getProperties() {
        return properties;
    }

    public JavaType getJavaType() {
        return javaType;
    }
}
