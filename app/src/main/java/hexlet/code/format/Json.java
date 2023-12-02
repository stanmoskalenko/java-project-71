package hexlet.code.format;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Json {
    public static String format(List<Map<String, Object>> comparedMap) throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        Map<String, Object> result = new HashMap<>();
        result.put("result", comparedMap);

        return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
    }
}
