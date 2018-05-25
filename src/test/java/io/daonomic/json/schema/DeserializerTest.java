package io.daonomic.json.schema;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import io.daonomic.json.schema.deserializer.JsonSchemaDeserializerModifier;
import io.daonomic.json.schema.domain.OptionalChildTest1;
import io.daonomic.json.schema.domain.ShowIfTest1;
import io.daonomic.json.schema.domain.ShowIfTest5;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.*;

public class DeserializerTest {
    private final ObjectMapper objectMapper;

    public DeserializerTest() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule().setDeserializerModifier(new JsonSchemaDeserializerModifier()));
    }

    @Test
    public void removeIfEmpty() throws IOException {
        OptionalChildTest1 object = objectMapper.readValue("{\"field1\": \"value\", \"child\": {}}", OptionalChildTest1.class);
        assertEquals(object.field1, "value");
        assertNull(object.child);
    }

    @Test
    public void keepIfNotEmpty() throws IOException {
        OptionalChildTest1 object = objectMapper.readValue("{\"field1\": \"value\", \"child\": {\"field1\": \"value\"}}", OptionalChildTest1.class);
        assertEquals(object.field1, "value");
        assertEquals(object.child.field1, "value");
    }

    @Test
    public void removeIfNotShown1() throws IOException {
        assertFalse(objectMapper.readValue("{\"testValue\": false, \"show1\": \"value\"}", ShowIfTest5.class).testValue);
        assertNull(objectMapper.readValue("{\"testValue\": false, \"show1\": \"value\"}", ShowIfTest5.class).show1);
        assertNull(objectMapper.readValue("{\"testValue\": false, \"show2\": \"value\"}", ShowIfTest5.class).show2);
        assertEquals(objectMapper.readValue("{\"testValue\": true, \"show2\": \"value\"}", ShowIfTest5.class).show2, "value");
        assertTrue(objectMapper.readValue("{\"testValue\": true, \"show2\": \"value\"}", ShowIfTest5.class).testValue);
    }

    @Test
    public void removeIfNotShown2() throws IOException {
        assertNull(objectMapper.readValue("{\"testValue\": \"TWO\", \"shownIfOneOrThree\": \"value\"}", ShowIfTest1.class).shownIfOneOrThree);
        assertEquals(objectMapper.readValue("{\"testValue\": \"TWO\", \"shownIfOneOrThree\": \"value\"}", ShowIfTest1.class).testValue, ShowIfTest1.ShowIfEnum.TWO);
        assertEquals(objectMapper.readValue("{\"testValue\": \"ONE\", \"shownIfOneOrThree\": \"value\"}", ShowIfTest1.class).shownIfOneOrThree, "value");
        assertEquals(objectMapper.readValue("{\"testValue\": \"ONE\", \"shownIfOneOrThree\": \"value\"}", ShowIfTest1.class).testValue, ShowIfTest1.ShowIfEnum.ONE);
    }
}
