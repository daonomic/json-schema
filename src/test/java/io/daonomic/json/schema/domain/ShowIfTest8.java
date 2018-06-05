package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest8 {
    @NotNull
    public TestEnum testField;
    @ShowIf(field = "testField", value = "VALUE2")
    public String test2;
    @ShowIf(field = "testField", value = "VALUE3")
    public String test3;

    enum TestEnum {
        VALUE1,
        VALUE2,
        VALUE3
    }
}

