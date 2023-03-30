package ru.Alyona;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;

public class FileParsingJsonTest {
    private final ClassLoader cl = FileParsingTest.class.getClassLoader();
    @Test
    void jsonCleverTest() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = cl.getResourceAsStream("boat.json");
             InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(is))) {
            Boat boat = mapper.readValue(isr, Boat.class);

            Assertions.assertEquals("Fiesta", boat.name);
            Assertions.assertEquals(11, boat.age);
            Assertions.assertTrue(boat.isSailing);
            Assertions.assertEquals(List.of("Inflatable", "With a motor"), boat.characteristic);
        }
    }
}
