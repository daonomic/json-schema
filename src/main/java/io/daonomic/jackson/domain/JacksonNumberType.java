package io.daonomic.jackson.domain;

import com.fasterxml.jackson.core.JsonParser;

public class JacksonNumberType extends JacksonPrimitiveType {
    private JsonParser.NumberType numberType;

    public JacksonNumberType(JsonParser.NumberType numberType) {
        super(Type.NUMBER);
        this.numberType = numberType;
    }

    public JacksonNumberType() {
        super(Type.NUMBER);
    }

    public JsonParser.NumberType getNumberType() {
        return numberType;
    }

    public void setNumberType(JsonParser.NumberType numberType) {
        this.numberType = numberType;
    }
}
