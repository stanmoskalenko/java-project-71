package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;

import java.io.IOException;
import java.util.Map;

public class Parser {
    private static final String JSON_EXT = ".json";
    private static final String YAML_EXT = ".yaml";
    private static final String YML_EXT = ".yml";

    public static Map<String, Object> parse(Map<String, String> content) throws IOException {
        TypeReference<Map<String, Object>> typeReference = new TypeReference<>() {
        };
        var extension = content.get("extension");
        var data = content.get("data");

        return switch (extension) {
            case JSON_EXT -> new JsonMapper().readValue(data, typeReference);
            case YAML_EXT, YML_EXT -> new YAMLMapper().readValue(data, typeReference);
            default -> throw new IllegalStateException(extension);
        };
    }
}
