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

public class UiSchemaTest {
    @Test
    public void testUiSchema() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        JsonSchemaType schema = JsonFormatVisitor.inspect(objectMapper.constructType(io.daonomic.json.schema.domain.UiSchemaTest.class), objectMapper, new AnnotationPropertyHandlerFactory());
        String uiSchema = objectMapper.writeValueAsString(schema.toUiSchema());
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream("uiSchema.json")) {
            assertNotNull(in, "not found resource. schema=" + uiSchema);
            assertEquals(uiSchema, IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }
}
