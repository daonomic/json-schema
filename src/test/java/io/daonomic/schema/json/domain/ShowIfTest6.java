package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.IgnoreEmpty;
import io.daonomic.schema.json.custom.ShowIf;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ShowIfTest6 {
    @NotNull
    public boolean testValue;
    @ShowIf(field = "testValue", value = "true", required = true)
    public Child child;

    @IgnoreEmpty
    public static class Child {
        @NotBlank
        public String field1;
        @NotBlank
        public String field2;
    }
}

