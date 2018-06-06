package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest2 {
    @NotNull
    public ShowIfEnum testValue;
    @ShowIf(field = "testValue", value = "ONE")
    public String shownIfOne;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

