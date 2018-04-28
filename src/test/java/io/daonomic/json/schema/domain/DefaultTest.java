package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.Default;
import io.daonomic.json.schema.custom.DefaultType;

public class DefaultTest {
    @Default("value1")
    public String default1;
    @Default(value = "{'json': true}", type = DefaultType.JSON)
    public String default2;
    @Default(value = "TestDefaultValue.json", type = DefaultType.RESOURCE)
    public String default3;
}
