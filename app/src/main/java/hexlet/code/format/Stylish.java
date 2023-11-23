package hexlet.code.format;

import hexlet.code.Differ;

import java.util.Map;
import java.util.SortedMap;


public class Stylish {
    private static final String ADDED = "  + ";
    private static final String DELETED = "  - ";
    private static final String UNMODIFIED = "    ";
    private static final String DELIMITER = ": ";

    public static String format(SortedMap<String, Map<String, Object>> comparedMap) {
        var diff = new StringBuilder("{\n");

        comparedMap.forEach((key, data) -> {
            var modified = (boolean) data.get(Differ.MODIFIED);
            var newValue = data.get(Differ.NEW_VALUE);
            var oldValue = data.get(Differ.OLD_VALUE);

            if (modified) {
                if (oldValue != null) {
                    diff.append(DELETED).append(key).append(DELIMITER).append(oldValue).append("\n");
                }
                if (newValue != null) {
                    diff.append(ADDED).append(key).append(DELIMITER).append(newValue).append("\n");
                }
            } else {
                diff.append(UNMODIFIED).append(key).append(DELIMITER).append(newValue).append("\n");
            }
        });
        diff.append("}");

        return diff.toString();
    }
}
