package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.IgnoreEmpty;

import javax.validation.constraints.NotNull;

public class OptionalChildTest1 {

    public String field1;
    public Child child;

    @IgnoreEmpty
    public static class Child {
        @NotNull
        public String field1;
        @NotNull
        public String field2;
    }
}
