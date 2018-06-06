package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest4 {
    @NotNull
    public ShowIfEnum testValue;
    @ShowIf(field = "testValue", value = "ONE", required = true)
    public String testNegative;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

