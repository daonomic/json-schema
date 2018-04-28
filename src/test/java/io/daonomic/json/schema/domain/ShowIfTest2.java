package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest2 {
    @NotNull
    public ShowIfEnum testValue;
    @ShowIf(field = "testValue", positive = "ONE")
    public String shownIfOne;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

