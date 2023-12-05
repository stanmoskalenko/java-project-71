package hexlet.code;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Differ {

    private static final String PATTERN = "(?i:.yaml$|.json$|.yml$)";

    public static Map<String, String> readContent(Path path) throws IOException {
        Map<String, String> content = new HashMap<>();
        var pattern = Pattern.compile(PATTERN);
        var match = pattern.matcher(path.toString());
        var extension = match.find() ? match.group() : "Unsupported file extension from the passed path: " + path;
        var data = Files.readString(path);
        content.put("data", data);
        content.put("extension", extension);

        return content;
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
        var firstContent = readContent(firstPath);
        var secondContent = readContent(secondPath);
        var firstData = Parser.parse(firstContent);
        var secondData = Parser.parse(secondContent);
        var comparedData = Generator.compareData(firstData, secondData);

        return Formatter.format(format, comparedData);
    }
}
