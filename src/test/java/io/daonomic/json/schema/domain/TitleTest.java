package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.Title;

public class TitleTest {
    public String noTitle;
    @Title("This is title")
    public String withTitle;
}
