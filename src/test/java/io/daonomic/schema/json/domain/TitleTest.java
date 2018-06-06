package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.Title;

public class TitleTest {
    public String noTitle;
    @Title("This is title")
    public String withTitle;
}
