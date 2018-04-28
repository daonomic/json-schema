package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.Optional;

import javax.validation.constraints.NotNull;

public class OptionalChildTest1 {

    public String field1;
    @Optional
    public Child child;

    public static class Child {
        @NotNull
        public String field1;
        @NotNull
        public String field2;
    }
}
