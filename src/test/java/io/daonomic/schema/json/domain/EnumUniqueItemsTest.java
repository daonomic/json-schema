package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.CustomEnumProperty;
import io.daonomic.schema.json.custom.CustomLabels;
import io.daonomic.schema.json.custom.UniqueItems;

import java.util.List;

public class EnumUniqueItemsTest {
    public MyEnum field1;
    @CustomEnumProperty({"ONE", "THREE"})
    public MyEnum field2;
    @UniqueItems
    public List<MyEnum> field3;

    @CustomLabels("")
    public enum MyEnum {
        ONE,
        TWO,
        THREE
    }
}
