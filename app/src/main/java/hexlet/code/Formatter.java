package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hexlet.code.format.Plain;
import hexlet.code.format.Stylish;

import java.util.Map;
import java.util.SortedMap;

public final class Formatter {
    public static final String STYLISH = "stylish";
    public static final String PLAIN = "plain";
    public static final String JSON = "json";

    public static String format(
            String format,
            SortedMap<String, Map<String, Object>> data
    ) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        return switch (format) {
            case JSON ->  objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(data);
            case PLAIN -> Plain.format(data);
            default -> Stylish.format(data);
        };
    }
}
