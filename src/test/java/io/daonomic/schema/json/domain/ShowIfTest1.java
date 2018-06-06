package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.ShowIf;
import io.daonomic.schema.json.custom.Title;

import javax.validation.constraints.NotNull;

public class ShowIfTest1 {
    @NotNull
    public ShowIfEnum testValue;
    @Title("Should be shown if ONE or THREE selected")
    @ShowIf(field = "testValue", value = {"ONE", "THREE"}, required = true)
    public String shownIfOneOrThree;

    public enum ShowIfEnum {
        ONE,
        TWO,
        THREE
    }
}

