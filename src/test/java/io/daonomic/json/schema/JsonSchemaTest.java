package io.daonomic.json.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.daonomic.json.schema.annotations.AnnotationPropertyHandlerFactory;
import io.daonomic.json.schema.domain.*;
import io.daonomic.json.schema.visitors.JsonFormatVisitor;
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
        String jsonSchema = objectMapper.writeValueAsString(JsonFormatVisitor.inspect(objectMapper.constructType(testClass), objectMapper, new AnnotationPropertyHandlerFactory()).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(testClass.getSimpleName() + ".json")) {
            assertNotNull(in, "not found resource. schema=" + jsonSchema);
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }

    @Test
    public void testUiSchema() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonSchemaType schema = JsonFormatVisitor.inspect(objectMapper.constructType(UiSchemaTest.class), objectMapper, new AnnotationPropertyHandlerFactory());
        String uiSchema = objectMapper.writeValueAsString(schema.toUiSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream("uiSchema.json")) {
            assertNotNull(in, "not found resource. schema=" + uiSchema);
            assertEquals(uiSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
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
        String jsonSchema = objectMapper.writeValueAsString(JsonFormatVisitor.inspect(objectMapper.constructType(testClass), objectMapper, new AnnotationPropertyHandlerFactory()).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(testClass.getSimpleName() + ".json")) {
            assertNotNull(in, "not found resource. schema=" + jsonSchema);
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }
}
