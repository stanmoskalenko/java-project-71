package hexlet.code.format;

import hexlet.code.Generator;

import java.util.List;
import java.util.Map;

public class Plain {
    private static final String COMPLEX_VALUE = "[complex value]";
    private static final String ADDED = "Property '%s' was added with value: %s\n";
    private static final String DELETED = "Property '%s' was removed\n";
    private static final String MODIFIED = "Property '%s' was updated. From %s to %s\n";

    public static String normalize(Object data) {
        String result = "";
        if (data instanceof List<?> || data instanceof Map<?, ?>) {
            result = COMPLEX_VALUE;
        } else if (data instanceof String && !data.equals(Generator.NULL)) {
            result = "'" + data + "'";
        } else if (data == null) {
            result = null;
        } else {
            result = data.toString();
        }
        return result;
    }

    public static String format(List<Map<String, Object>> comparedMap) {
        var diff = new StringBuilder();

        for (Map<String, Object> entry : comparedMap) {
            var key = entry.get(Generator.KEY);
            var type = (String) entry.get(Generator.TYPE);
            var oldValue = entry.get(Generator.OLD_VALUE);
            var newValue = entry.get(Generator.NEW_VALUE);

            newValue = normalize(newValue);
            oldValue = normalize(oldValue);

            if (type.equals(Generator.MODIFIED)) {
                var msg = String.format(MODIFIED, key, oldValue, newValue);
                diff.append(msg);
            } else if (type.equals(Generator.ADDED)) {
                var msg = String.format(ADDED, key, newValue);
                diff.append(msg);
            } else if (type.equals(Generator.DELETED)) {
                var msg = String.format(DELETED, key);
                diff.append(msg);
            }
        }

        return diff.toString().trim();
    }
}
