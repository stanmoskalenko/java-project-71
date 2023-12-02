package hexlet.code;


import java.io.IOException;
import java.nio.file.Path;

public class Differ {

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
        var comparedData = Generator.compareData(firstData, secondData);

        return Formatter.format(format, comparedData);
    }
}
