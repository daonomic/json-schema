package io.daonomic.schema.json.domain;

import io.daonomic.schema.ui.annotations.EnumDescriptions;
import io.daonomic.schema.ui.annotations.UiWidget;

public class EnumTestWithDescriptions {
    @UiWidget(value = "text-block-selector")
    public MyEnum field1;

    @EnumDescriptions("MyEnum")
    public enum MyEnum {
        ONE,
        TWO,
        THREE
    }
}
