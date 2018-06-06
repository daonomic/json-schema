package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.daonomic.schema.json.annotations.AnnotationPropertyHandlerFactory;
import io.daonomic.schema.json.domain.*;
import io.daonomic.schema.json.visitors.JsonSchemaVisitor;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class JsonSchemaTest {
    @DataProvider
    public static Object[][] data1() {
        return new Object[][]{
            new Object[]{SimpleChildTest.class},
            new Object[]{Parent.class},
            new Object[]{RequiredAnnotationTest.class},
            new Object[]{TitleTest.class},
            new Object[]{DefaultTest.class},
            new Object[]{IgnoreTest.class},
            new Object[]{ShowIfTest1.class},
            new Object[]{ShowIfTest2.class},
            new Object[]{ShowIfTest3.class},
            new Object[]{ShowIfTest4.class},
            new Object[]{ShowIfTest5.class},
            new Object[]{ShowIfTest6.class},
            new Object[]{ShowIfTest7.class},
            new Object[]{ShowIfTest8.class},
            new Object[]{OptionalChildTest1.class},
            new Object[]{OptionalChildTest2.class},
            new Object[]{OptionalChildTest3.class},
            new Object[]{ArrayTest.class},
            new Object[]{EmailTest.class}
        };
    }

    @Test(dataProvider = "data1")
    public void test1(Class testClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonSchema = objectMapper.writeValueAsString(JsonSchemaVisitor.inspect(objectMapper.constructType(testClass), objectMapper, new AnnotationPropertyHandlerFactory()).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(testClass.getSimpleName() + ".json")) {
            assertNotNull(in, "not found resource. schema=" + jsonSchema);
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }

    @DataProvider
    public static Object[][] data2() {
        return new Object[][]{
            new Object[]{DateTest.class}
        };
    }

    @Test(dataProvider = "data2")
    public void test2(Class testClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String jsonSchema = objectMapper.writeValueAsString(JsonSchemaVisitor.inspect(objectMapper.constructType(testClass), objectMapper, new AnnotationPropertyHandlerFactory()).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(testClass.getSimpleName() + ".json")) {
            assertNotNull(in, "not found resource. schema=" + jsonSchema);
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }
}
