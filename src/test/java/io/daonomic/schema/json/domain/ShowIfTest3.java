package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest3 {
    @NotNull
    public ShowIfEnum testValue;
    @ShowIf(field = "testValue", value = "ONE")
    public String shownIfTwo;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

