package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.UiWidget;

public class UiSchemaTest {
    @UiWidget(value = "external-select", options = {
        @UiWidget.Option(key = "url", value = "test")
    })
    public String value;
}
