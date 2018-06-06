package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.ShowIf;

import javax.validation.constraints.NotNull;

public class ShowIfTest5 {
    @NotNull
    public boolean testValue;
    @ShowIf(field = "testValue", value = "true", required = true)
    public String show1;
    @ShowIf(field = "testValue", value = "true", required = true)
    public String show2;
}

