package hexlet.code;

import com.fasterxml.jackson.core.JsonProcessingException;
import hexlet.code.format.Json;
import hexlet.code.format.Plain;
import hexlet.code.format.Stylish;

import java.util.List;
import java.util.Map;

public final class Formatter {
    public static final String STYLISH = "stylish";
    public static final String PLAIN = "plain";
    public static final String JSON = "json";

    public static String format(
            String format,
            List<Map<String, Object>> data
    ) throws JsonProcessingException {
        return switch (format) {
            case JSON -> Json.format(data);
            case PLAIN -> Plain.format(data);
            case STYLISH -> Stylish.format(data);
            default -> throw new IllegalArgumentException(format + " format does not support!");
        };
    }
}
