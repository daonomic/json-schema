package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ShowIf;
import io.daonomic.json.schema.custom.Title;

import javax.validation.constraints.NotNull;

public class ShowIfTest1 {
    @NotNull
    public ShowIfEnum testValue;
    @Title("Should be shown if ONE or THREE selected")
    @ShowIf(field = "testValue", positive = {"ONE", "THREE"}, required = true)
    public String shownIfOneOrThree;

    enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

