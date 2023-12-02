package hexlet.code.format;

import hexlet.code.Generator;

import java.util.List;
import java.util.Map;


public class Stylish {
    private static final String PLUS = "  + ";
    private static final String MINUS = "  - ";
    private static final String UNCHANGED = "    ";
    private static final String DELIMITER = ": ";

    public static String format(List<Map<String, Object>> comparedMap) {
        var diff = new StringBuilder("{\n");
        comparedMap.forEach(entry -> {
            var key = entry.get(Generator.KEY);
            var type = (String) entry.get(Generator.TYPE);
            var newValue = entry.get(Generator.NEW_VALUE);
            var oldValue = entry.get(Generator.OLD_VALUE);

            switch (type) {
                case Generator.DELETED ->
                        diff.append(MINUS).append(key).append(DELIMITER).append(oldValue).append("\n");
                case Generator.MODIFIED -> {
                    diff.append(MINUS).append(key).append(DELIMITER).append(oldValue).append("\n");
                    diff.append(PLUS).append(key).append(DELIMITER).append(newValue).append("\n");
                }
                case Generator.ADDED -> diff.append(PLUS).append(key).append(DELIMITER).append(newValue).append("\n");
                default -> diff.append(UNCHANGED).append(key).append(DELIMITER).append(newValue).append("\n");
            }
        });
        diff.append("}");

        return diff.toString();
    }
}
