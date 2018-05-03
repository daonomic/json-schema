package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest3 {
    @NotNull
    public ShowIfEnum testValue;
    @ShowIf(field = "testValue", positive = "ONE", negative = {"TWO", "THREE"})
    public String shownIfTwo;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

