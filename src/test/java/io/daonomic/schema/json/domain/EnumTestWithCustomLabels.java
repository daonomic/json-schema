package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.CustomEnumProperty;
import io.daonomic.schema.json.custom.CustomLabels;

import java.util.List;

public class EnumTestWithCustomLabels {
    public MyEnum field1;
    @CustomEnumProperty(value = {"ONE", "THREE"}, labelPrefix = "MyEnum")
    public MyEnum field2;
    public List<MyEnum> field3;

    @CustomLabels("MyEnum")
    public enum MyEnum {
        ONE,
        TWO,
        THREE
    }
}
