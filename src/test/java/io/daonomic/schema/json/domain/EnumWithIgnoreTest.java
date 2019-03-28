package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.CustomEnum;
import io.daonomic.schema.json.custom.CustomLabels;
import io.daonomic.schema.json.custom.JsonSchemaIgnore;

import java.util.List;

public class EnumWithIgnoreTest {
    public MyEnum field1;
    @CustomEnum({"ONE", "THREE"})
    public MyEnum field2;
    public List<MyEnum> field3;

    @CustomLabels("")
    public enum MyEnum {
        @JsonSchemaIgnore
        TEST,
        ONE,
        TWO,
        THREE
    }
}
