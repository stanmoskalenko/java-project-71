package hexlet.code;

import picocli.CommandLine;

import java.util.concurrent.Callable;

@CommandLine.Command(
        name = "diff cli",
        mixinStandardHelpOptions = true,
        version = "1.0.0",
        description = "Compares two configuration files and shows a difference.")
public final class App implements Callable<Integer> {
    @CommandLine.Parameters(
            // maybe convert to path
            // type = Path.class,
            index = "0",
            paramLabel = "filepath1",
            description = "Path to first file")
    private String firstArg;

    @CommandLine.Parameters(
            index = "1",
            paramLabel = "filepath2",
            description = "Path to second file")
    private String secondArg;

    @CommandLine.Option(
            names = {"-f", "--format"},
            description = "Output format [default: ${defaultValue}]",
            defaultValue = Formatter.STYLISH)
    private String format;

    @Override
    public Integer call() {
        var firstPath = firstArg;
        var secondPath = secondArg;
        try {
            var result = Differ.generate(firstPath, secondPath, format.toLowerCase().trim());
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return 0;
    }

    public static void main(String[] args) {
        new CommandLine(new App()).execute(args);
    }
}
