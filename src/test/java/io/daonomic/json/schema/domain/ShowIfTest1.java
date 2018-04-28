package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest1 {
    @NotNull
    public ShowIfEnum testValue;
    @ShowIf(field = "testValue", positive = {"ONE", "THREE"}, required = true)
    public String shownIfOneOrThree;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

