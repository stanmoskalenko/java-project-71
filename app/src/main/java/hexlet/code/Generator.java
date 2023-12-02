package hexlet.code;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.HashMap;

public class Generator {

    public static final String KEY = "key";
    public static final String TYPE = "type";
    public static final String NEW_VALUE = "newValue";
    public static final String OLD_VALUE = "oldValue";
    public static final String NULL = "null";
    public static final String DELETED = "deleted";
    public static final String ADDED = "added";
    public static final String UNMODIFIED = "unmodified";
    public static final String MODIFIED = "modified";


    private static void register(
            List<Map<String, Object>> acc,
            String key,
            Object newValue,
            Object oldValue,
            String type
    ) {
        Map<String, Object> comparedValue = new HashMap<>();
        if (newValue != null) {

            comparedValue.put(NEW_VALUE, newValue);
        }
        if (oldValue != null) {
            comparedValue.put(OLD_VALUE, oldValue);
        }

        comparedValue.put(TYPE, type);
        comparedValue.put(KEY, key);
        acc.add(comparedValue);
    }

    public static List<Map<String, Object>> compareData(
            Map<String, ?> firstData,
            Map<String, ?> secondData) {
        SortedMap<String, Object> unionMap = new TreeMap<>();
        unionMap.putAll(firstData);
        unionMap.putAll(secondData);
        List<Map<String, Object>> acc = new ArrayList<>();

        unionMap.forEach((key, value) -> {
            var firstValue = firstData.get(key) != null ? firstData.get(key) : NULL;
            var secondValue = secondData.get(key) != null ? secondData.get(key) : NULL;
            if (firstData.containsKey(key) && secondData.containsKey(key)) {
                if (firstValue.equals(secondValue)) {
                    register(acc, key, firstValue, null, UNMODIFIED);
                } else {
                    register(acc, key, secondValue, firstValue, MODIFIED);
                }
            } else if (!firstData.containsKey(key) && secondData.containsKey(key)) {
                register(acc, key, secondValue, null, ADDED);
            } else {
                register(acc, key, null, firstValue, DELETED);
            }
        });
        return acc;
    }
}
