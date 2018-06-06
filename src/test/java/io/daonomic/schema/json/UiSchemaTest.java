package io.daonomic.schema.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.daonomic.schema.json.JsonSchemaType;
import io.daonomic.schema.json.annotations.AnnotationPropertyHandlerFactory;
import io.daonomic.schema.json.domain.*;
import io.daonomic.schema.json.visitors.JsonSchemaVisitor;
import io.daonomic.schema.ui.UiSchema;
import org.apache.commons.io.IOUtils;
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
        JsonNode uiSchema = UiSchema.generate(objectMapper.constructType(TestUiSchema.class), objectMapper);
        try (final InputStream in = getClass().getClassLoader().getResourceAsStream("uiSchema.json")) {
            assertNotNull(in, "not found resource. schema=" + uiSchema);
            assertEquals(objectMapper.writeValueAsString(uiSchema), IOUtils.toString(in, StandardCharsets.UTF_8));
        }
    }
}
