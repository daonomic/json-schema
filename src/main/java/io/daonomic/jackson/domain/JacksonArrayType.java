package io.daonomic.jackson.domain;

public class JacksonArrayType implements JacksonType {
    private JacksonType itemType;

    public JacksonType getItemType() {
        return itemType;
    }

    public void setItemType(JacksonType itemType) {
        this.itemType = itemType;
    }
}
