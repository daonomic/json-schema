package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.CustomEnum;
import io.daonomic.schema.json.custom.CustomLabels;

import java.util.List;

public class EnumTest {
    public MyEnum field1;
    @CustomEnum({"ONE", "THREE"})
    public MyEnum field2;
    public List<MyEnum> field3;

    @CustomLabels("")
    public enum MyEnum {
        ONE,
        TWO,
        THREE
    }
}
