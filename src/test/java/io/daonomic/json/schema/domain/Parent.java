package io.daonomic.json.schema.domain;

import java.util.List;

public class Parent {
    public Child child;
    public List<Child> children;
    public int intValue;
    public Integer integerValue;

    public static class Child {
        public String name;
    }
}
