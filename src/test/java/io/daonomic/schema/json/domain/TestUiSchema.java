package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.ShowIf;
import io.daonomic.schema.ui.annotations.UiWidget;

public class TestUiSchema {
    @UiWidget(value = "external-select", options = {
        @UiWidget.Option(key = "url", value = "test")
    })
    public String value;
    public String value2;
    public Child child;
    @ShowIf(field = "value", value = "some")
    public Child optionalChild;

    public static class Child {
        @UiWidget(value = "external-select", options = {
            @UiWidget.Option(key = "url", value = "childTest")
        })
        public String value;
    }
}
