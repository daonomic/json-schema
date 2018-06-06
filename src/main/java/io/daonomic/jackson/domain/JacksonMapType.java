package io.daonomic.jackson.domain;

public class JacksonMapType implements JacksonType {
    private JacksonType keyType;
    private JacksonType valueType;

    public JacksonType getKeyType() {
        return keyType;
    }

    public void setKeyType(JacksonType keyType) {
        this.keyType = keyType;
    }

    public JacksonType getValueType() {
        return valueType;
    }

    public void setValueType(JacksonType valueType) {
        this.valueType = valueType;
    }
}
