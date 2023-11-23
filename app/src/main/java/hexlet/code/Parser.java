package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.regex.Pattern;

public class Parser {
    private static final String JSON_EXT = ".json";
    private static final String YAML_EXT = ".yaml";
    private static final String PATTERN = "(?i:.yaml$|.json$)";

    public static Map<String, Object> parse(Path path) throws IOException {
        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
        };
        String type = "";
        var pattern = Pattern.compile(PATTERN);
        var match = pattern.matcher(path.toString());
        type = match.find() ? match.group() : "Unsupported file extension from the passed path: " + path;
        var file = Files.readString(path);

        return switch (type) {
            case JSON_EXT -> new JsonMapper().readValue(file, typeReference);
            case YAML_EXT -> new YAMLMapper().readValue(file, typeReference);
            default -> throw new IllegalStateException(type);
        };
    }
}
