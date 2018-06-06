package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.IgnoreEmpty;
import io.daonomic.schema.json.custom.ShowIf;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ShowIfTest7 {
    @NotNull
    public String testField;
    @ShowIf(field = "testField", value = "testValue", required = true)
    public Child child;

    @IgnoreEmpty
    public static class Child {
        @NotBlank
        public String field1;
        @NotBlank
        public String field2;
    }
}

