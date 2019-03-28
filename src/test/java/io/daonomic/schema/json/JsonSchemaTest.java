package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.kotlin.KotlinModule;
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
            new Object[]{RequiredKotlinTest.class},
            new Object[]{TitleTest.class},
            new Object[]{TitleKotlinTest.class},
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
            new Object[]{EmailTest.class},
            new Object[]{EnumTest.class},
            new Object[]{EnumTestWithDescriptions.class},
            new Object[]{EnumWithIgnoreTest.class}
        };
    }

    @Test(dataProvider = "data1")
    public void test1(Class testClass) throws IOException {
        ObjectMapper objectMapper = createMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonSchema = objectMapper.writeValueAsString(JsonSchemaVisitor.inspect(objectMapper.constructType(testClass), objectMapper, new AnnotationPropertyHandlerFactory()).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(testClass.getSimpleName() + ".json")) {
            assertNotNull(in, "not found resource. schema=" + jsonSchema);
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }

    @Test
    public void testWithLabelResolver() throws IOException {
        ObjectMapper objectMapper = createMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        LabelResolver resolver = key -> {
            if (key.equals("ONE")) return "one";
            if (key.equals("TWO")) return "two";
            return key;
        };
        String jsonSchema = objectMapper.writeValueAsString(JsonSchemaVisitor.inspect(objectMapper.constructType(EnumTest.class), objectMapper, new AnnotationPropertyHandlerFactory(resolver)).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream("EnumTestLabelResolver.json")) {
            assertNotNull(in, "not found resource. schema=" + jsonSchema);
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }

    @Test
    public void testWithLabelResolver2() throws IOException {
        ObjectMapper objectMapper = createMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        LabelResolver resolver = key -> {
            if (key.equals("MyEnum.ONE")) return "one";
            if (key.equals("MyEnum.TWO")) return "two";
            if (key.equals("MyEnum.THREE")) return "THREE";
            return key;
        };
        String jsonSchema = objectMapper.writeValueAsString(JsonSchemaVisitor.inspect(objectMapper.constructType(EnumTestWithCustomLabels.class), objectMapper, new AnnotationPropertyHandlerFactory(resolver)).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream("EnumTestLabelResolver.json")) {
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
        ObjectMapper objectMapper = createMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String jsonSchema = objectMapper.writeValueAsString(JsonSchemaVisitor.inspect(objectMapper.constructType(testClass), objectMapper, new AnnotationPropertyHandlerFactory()).toJsonSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(testClass.getSimpleName() + ".json")) {
            assertNotNull(in, "not found resource. schema=" + jsonSchema);
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }

    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new KotlinModule());
        return mapper;
    }
}
