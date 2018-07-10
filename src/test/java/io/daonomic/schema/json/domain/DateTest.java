package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.Format;

import java.util.Date;

public class DateTest {
    public Date start;
    public Date end;
    @Format("date")
    public Date date;
}
