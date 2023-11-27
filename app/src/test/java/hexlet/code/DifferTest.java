package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DisplayName("Difference generator tests")
class DifferTest {
    private static String expectedStylish;
    private static String expectedPlain;
    private static Map<?, ?> expectedJson;
    private static final String RESOURCES = "src/test/resources/";
    private static final String[] JSON_PATH_ARG = {RESOURCES + "json/1.json", RESOURCES + "json/2.json"};
    private static final String[] YAML_PATH_ARG = {RESOURCES + "yaml/1.yaml", RESOURCES + "yaml/2.yaml"};

    private static final ObjectMapper OM = new ObjectMapper();

    @BeforeAll
    public static void setUp() throws IOException {
        var stylishExpectedPath = Path.of(RESOURCES + "expected-stylish.txt")
                .toAbsolutePath()
                .normalize();
        var plainExpectedPath = Path.of(RESOURCES + "expected-plain.txt")
                .toAbsolutePath()
                .normalize();
        var expectedJsonPath = Path.of(RESOURCES + "expected.json")
                .toAbsolutePath()
                .normalize();

        expectedStylish = Files.readString(stylishExpectedPath);
        expectedPlain = Files.readString(plainExpectedPath);
        var expectedJsonStream = Files.newInputStream(expectedJsonPath, StandardOpenOption.READ);
        expectedJson = OM.readValue(expectedJsonStream, Map.class);

        expectedJsonStream.close();
    }

    @Test
    @DisplayName("Stylish-style (default-mode) is performed correctly")
    void mainStylishFormatTest() throws Exception {
        var actualYamlSample = Differ.generate(YAML_PATH_ARG[0], YAML_PATH_ARG[1]);
        var actualJsonSample = Differ.generate(JSON_PATH_ARG[0], JSON_PATH_ARG[1]);
        var actualMultipleExt = Differ.generate(JSON_PATH_ARG[0], YAML_PATH_ARG[1]);
        var actualWithFormat = Differ.generate(JSON_PATH_ARG[0], YAML_PATH_ARG[1], "stylish");

        assertEquals(expectedStylish, actualYamlSample);
        assertEquals(expectedStylish, actualJsonSample);
        assertEquals(expectedStylish, actualMultipleExt);
        assertEquals(expectedStylish, actualWithFormat);
    }

    @Test
    @DisplayName("JSON-style is performed correctly")
    void mainJsonFormatTest() throws Exception {
        var actualYamlSample = Differ.generate(YAML_PATH_ARG[0], YAML_PATH_ARG[1], "json");
        var actualJsonSample = Differ.generate(JSON_PATH_ARG[0], JSON_PATH_ARG[1], "json");
        var actualMultipleExt = Differ.generate(JSON_PATH_ARG[0], YAML_PATH_ARG[1], "json");

        assertEquals(expectedJson, OM.readValue(actualYamlSample, Map.class));
        assertEquals(expectedJson, OM.readValue(actualJsonSample, Map.class));
        assertEquals(expectedJson, OM.readValue(actualMultipleExt, Map.class));
    }

    @Test
    @DisplayName("Plain-style is performed correctly")
    void mainPlainFormatTest() throws Exception {
        var actualYamlSample = Differ.generate(YAML_PATH_ARG[0], YAML_PATH_ARG[1], "plain");
        var actualJsonSample = Differ.generate(JSON_PATH_ARG[0], JSON_PATH_ARG[1], "plain");
        var actualMultipleExt = Differ.generate(JSON_PATH_ARG[0], YAML_PATH_ARG[1], "plain");

        assertEquals(expectedPlain, actualYamlSample);
        assertEquals(expectedPlain, actualJsonSample);
        assertEquals(expectedPlain, actualMultipleExt);
    }
}
