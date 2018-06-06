package io.daonomic.schema.json.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class RequiredAnnotationTest {
    @NotNull
    public String test1;
    @NotBlank
    public String test2;
    @NotEmpty
    public String test3;
    public String test4;
}
