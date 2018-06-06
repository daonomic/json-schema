package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.Default;
import io.daonomic.schema.json.custom.DefaultType;

public class DefaultTest {
    @Default("value1")
    public String default1;
    @Default(value = "{'json': true}", type = DefaultType.JSON)
    public String default2;
    @Default(value = "TestDefaultValue.json", type = DefaultType.RESOURCE)
    public String default3;
}
