package io.daonomic.schema.json.domain;

import io.daonomic.schema.json.custom.ItemTitle;
import io.daonomic.schema.json.custom.Title;

import java.util.List;

public class ArrayTest {
    @Title("Just array")
    @ItemTitle("item title")
    public List<String> array;
}
