package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.CustomEnum;

public class EnumTest {
    public MyEnum field1;
    @CustomEnum({"ONE", "THREE"})
    public MyEnum field2;

    public enum MyEnum {
        ONE,
        TWO,
        THREE
    }
}
