package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest5 {
    @NotNull
    public boolean testValue;
    @ShowIf(field = "testValue", value = "true", required = true)
    public String show1;
    @ShowIf(field = "testValue", value = "true", required = true)
    public String show2;
}

