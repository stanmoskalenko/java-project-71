package hexlet.code;


import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class Differ {

    public static final String NEW_VALUE = "newValue";
    public static final String OLD_VALUE = "oldValue";
    public static final String MODIFIED = "modified";
    public static final String NULL = "null";


    private static void register(
            SortedMap<String, Map<String, Object>> acc,
            String key,
            Object newValue,
            Object oldValue,
            Boolean modified
    ) {
        Map<String, Object> comparedValue = new HashMap<>();
        if (newValue != null) {
            comparedValue.put(NEW_VALUE, newValue);
        }
        if (oldValue != null) {
            comparedValue.put(OLD_VALUE, oldValue);
        }

        comparedValue.put(MODIFIED, modified);
        acc.put(key, comparedValue);
    }

    public static String generate(String first, String second) throws IOException {
        return generate(first, second, Formatter.STYLISH);
    }

    public static String generate(
            String first,
            String second,
            String format
    ) throws IOException {
        var firstPath = Path.of(first).toAbsolutePath().normalize();
        var secondPath = Path.of(second).toAbsolutePath().normalize();
        var firstData = Parser.parse(firstPath);
        var secondData = Parser.parse(secondPath);
        SortedMap<String, Map<String, Object>> acc = new TreeMap<>();
        SortedMap<String, Object> unionMap = new TreeMap<>();
        unionMap.putAll(firstData);
        unionMap.putAll(secondData);

        unionMap.forEach((key, value) -> {
            var firstValue = firstData.get(key) != null ? firstData.get(key) : NULL;
            var secondValue = secondData.get(key) != null ? secondData.get(key) : NULL;
            if (firstData.containsKey(key) && secondData.containsKey(key)) {
                if (firstValue.equals(secondValue)) {
                    register(acc, key, firstValue, null, false);
                } else {
                    register(acc, key, secondValue, firstValue, true);
                }
            } else if (!firstData.containsKey(key) && secondData.containsKey(key)) {
                register(acc, key, secondValue, null, true);
            } else {
                register(acc, key, null, firstValue, true);
            }
        });

        return Formatter.format(format, acc);
    }
}
