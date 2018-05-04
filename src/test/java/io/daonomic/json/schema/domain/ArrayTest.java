package io.daonomic.json.schema.domain;

import io.daonomic.json.schema.custom.ItemTitle;
import io.daonomic.json.schema.custom.Title;

import java.util.List;

public class ArrayTest {
    @Title("Just array")
    @ItemTitle("item title")
    public List<String> array;
}
