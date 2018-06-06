package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.JsonSchemaIgnore;

public class IgnoreTest {
    public String prop1;
    @JsonSchemaIgnore
    public String prop2;
}
