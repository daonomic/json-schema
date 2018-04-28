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

public class JsonSchemaTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JsonSchemaTest() {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @DataProvider
    public static Object[][] data() {
        return new Object[][]{
            new Object[]{Parent.class, "Parent"},
            new Object[]{RequiredAnnotationTest.class, "RequiredAnnotationTest"},
            new Object[]{TitleTest.class, "TitleTest"},
            new Object[]{DefaultTest.class, "DefaultTest"},
            new Object[]{IgnoreTest.class, "IgnoreTest"}
        };
    }

    @Test(dataProvider = "data")
    public void test(Class testClass, String testSchemaName) throws IOException {
        String jsonSchema = objectMapper.writeValueAsString(JsonFormatVisitor.inspect(objectMapper.constructType(testClass), objectMapper, new AnnotationPropertyHandlerFactory()).toJsonNode(objectMapper.getNodeFactory()));
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream(testSchemaName + ".json")) {
            assertEquals(jsonSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }

    }
}
