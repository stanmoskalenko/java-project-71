package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AppTest {
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private static String expectedStylish;
    private static String expectedNestedStylish;
    private static String expectedPlain;
    private static String expectedNestedPlain;
    private static Map<?, ?> expectedJson;
    private static Map<?, ?> expectedNestedJson;
    private static ObjectMapper om = new ObjectMapper();

    private static String resources = "src/test/resources/";
    private static String nestedResources = "src/test/resources/nested/";
    private static String expectedStylishFileName = "expected-stylish.txt";
    private static String expectedPlainFileName = "expected-plain.txt";
    private static String expectedJsonFileName = "expected.json";
    private static String[] jsonPathArg = {resources + "json/1.json", resources + "json/2.json"};
    private static String[] yamlPathArg = {resources + "yaml/1.yaml", resources + "yaml/2.yaml"};
    private static String[] nestedYamlPathArg = {nestedResources + "yaml/1.yaml", nestedResources + "yaml/2.yaml"};
    private static String[] nestedJsonPathArg = {nestedResources + "json/1.json", nestedResources + "json/2.json"};

    @BeforeAll
    public static void loadSample() throws IOException {
        var stylishExpectedPath = Path.of(resources + expectedStylishFileName)
                .toAbsolutePath()
                .normalize();
        var stylishExpectedNestedPath = Path.of(nestedResources + expectedStylishFileName)
                .toAbsolutePath()
                .normalize();
        var palinExpectedNestedPath = Path.of(nestedResources + expectedPlainFileName)
                .toAbsolutePath()
                .normalize();
        var plainExpectedPath = Path.of(resources + expectedPlainFileName)
                .toAbsolutePath()
                .normalize();
        var expectedNestedJsonPath = Path.of(nestedResources + expectedJsonFileName)
                .toAbsolutePath()
                .normalize();
        var expectedJsonPath = Path.of(resources + expectedJsonFileName)
                .toAbsolutePath()
                .normalize();

        expectedStylish = Files.readString(stylishExpectedPath);
        expectedNestedStylish = Files.readString(stylishExpectedNestedPath);
        expectedNestedPlain = Files.readString(palinExpectedNestedPath);
        expectedPlain = Files.readString(plainExpectedPath);

        var expectedNestedJsonStream = Files.newInputStream(expectedNestedJsonPath, StandardOpenOption.READ);
        var expectedJsonStream = Files.newInputStream(expectedJsonPath, StandardOpenOption.READ);
        expectedJson = om.readValue(expectedJsonStream, Map.class);
        expectedNestedJson = om.readValue(expectedNestedJsonStream, Map.class);

        expectedNestedJsonStream.close();
        expectedJsonStream.close();
    }

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    public void clear() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Generation of differences in stylish-style (default-mode) of JSON format file is performed correctly")
    void mainJsonDefaultFormatTest() {
        String[] sample = {jsonPathArg[0], jsonPathArg[1]};
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertEquals(expectedStylish, actual);
    }

    @Test
    @DisplayName("Generation of differences in stylish-style of JSON format file is performed correctly")
    void mainJsonStylishFormatTest() {
        String[] sample = {"-f", "stylish", jsonPathArg[0], jsonPathArg[1]};
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertEquals(expectedStylish, actual);
    }

    @Test
    @DisplayName("Generation of differences in stylish-style of YAML format file is performed correctly")
    void mainYamlStylishFormatTest() {
        String[] sample = {"--format", "stylish", yamlPathArg[0], yamlPathArg[1]};
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertEquals(expectedStylish, actual);
    }

    @Test
    @DisplayName("Unsupported file extension from processed correctly")
    void mainUnsupportedType() {
        var errorExtensionFile = "src/test/resources/unsupported.error";
        String[] sample = {errorExtensionFile, errorExtensionFile};
        var expected = "Unsupported file extension from the passed path";
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertTrue(actual.contains(expected));
    }

    @Test
    @DisplayName("Generation of differences in stylish-style of nested YAML format file is performed correctly")
    void mainNestedYamlStylishFormatTest() {
        String[] sample = {"--format", "stylish", nestedYamlPathArg[0], nestedYamlPathArg[1]};
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertEquals(expectedNestedStylish, actual);
    }

    @Test
    @DisplayName("Generation of differences in stylish-style of nested JSON format file is performed correctly")
    void mainNestedJsonStylishFormatTest() {
        String[] sample = {"--format", "stylish", nestedJsonPathArg[0], nestedJsonPathArg[1]};
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertEquals(expectedNestedStylish, actual);
    }

    @Test
    @DisplayName("Generation of differences in plain style of JSON format file is performed correctly")
    void mainJsonPlainFormatTest() {
        String[] sample = {"--format", "plain", jsonPathArg[0], jsonPathArg[1]};
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertEquals(expectedPlain, actual);
    }

    @Test
    @DisplayName("Generation of differences in plain style of JSON format file is performed correctly")
    void mainNestedJsonPlainFormatTest() {
        String[] sample = {"--format", "plain", nestedJsonPathArg[0], nestedJsonPathArg[1]};
        App.main(sample);
        var actual = outputStreamCaptor.toString().trim();

        assertEquals(expectedNestedPlain, actual);
    }

    @Test
    @DisplayName("Generation of differences in json style of nested JSON format file is performed correctly")
    void mainNestedJsonToJsonFormatTest() throws Exception {
        String[] sample = {"--format", "json", nestedJsonPathArg[0], nestedJsonPathArg[1]};
        App.main(sample);
        var actualString = outputStreamCaptor.toString().trim();
        var actualJson = om.readValue(actualString, Map.class);

        assertEquals(expectedNestedJson, actualJson);
    }

    @Test
    @DisplayName("Generation of differences in json style of JSON format file is performed correctly")
    void mainJsonToJsonFormatTest() throws Exception {
        String[] sample = {"-f", "json", jsonPathArg[0], jsonPathArg[1]};
        App.main(sample);
        var actualString = outputStreamCaptor.toString().trim();
        var actualJson = om.readValue(actualString, Map.class);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Generation of differences in json style of YAML format file is performed correctly")
    void mainYamlToJsonFormatTest() throws Exception {
        String[] sample = {"-f", "json", yamlPathArg[0], yamlPathArg[1]};
        App.main(sample);
        var actualString = outputStreamCaptor.toString().trim();
        var actualJson = om.readValue(actualString, Map.class);

        assertEquals(expectedJson, actualJson);
    }

    @Test
    @DisplayName("Generation of differences in json style of nested YAML format file is performed correctly")
    void mainNestedYamlToJsonFormatTest() throws Exception {
        String[] sample = {"-f", "json", nestedYamlPathArg[0], nestedYamlPathArg[1]};
        App.main(sample);
        var actualString = outputStreamCaptor.toString().trim();
        var actualJson = om.readValue(actualString, Map.class);

        assertEquals(expectedNestedJson, actualJson);
    }
}
