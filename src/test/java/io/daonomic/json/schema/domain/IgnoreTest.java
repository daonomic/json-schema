package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.JsonSchemaIgnore;

public class IgnoreTest {
    public String prop1;
    @JsonSchemaIgnore
    public String prop2;
}
