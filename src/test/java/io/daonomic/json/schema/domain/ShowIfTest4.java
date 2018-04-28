package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest4 {
    @NotNull
    public ShowIfEnum testValue;
    @ShowIf(field = "testValue", positive = "ONE", negative = {"TWO", "THREE"}, required = true)
    public String testNegative;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

