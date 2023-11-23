package hexlet.code.format;

import hexlet.code.Differ;

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
        } else if (data instanceof String && !data.equals(Differ.NULL)) {
            result = "'" + data + "'";
        } else if (data == null) {
            result = null;
        } else {
            result = data.toString();
        }
        return result;
    }

    public static String format(Map<String, Map<String, Object>> comparedMap) {
        var diff = new StringBuilder();
        comparedMap.forEach((key, data) -> {
            var modified = (boolean) data.get(Differ.MODIFIED);
            var newValue = data.get(Differ.NEW_VALUE);
            var oldValue = data.get(Differ.OLD_VALUE);
            newValue = normalize(newValue);
            oldValue = normalize(oldValue);

            if (modified) {
                if (oldValue != null && newValue != null) {
                    var msg = String.format(MODIFIED, key, oldValue.toString(), newValue.toString());
                    diff.append(msg);
                } else if (oldValue != null) {
                    var msg = String.format(DELETED, key);
                    diff.append(msg);
                } else {
                    var msg = String.format(ADDED, key, newValue.toString());
                    diff.append(msg);
                }
            }
        });

        return diff.toString().trim();
    }
}
