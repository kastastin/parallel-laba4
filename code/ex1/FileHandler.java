package code.ex1;

import java.util.List;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.stream.Stream;

public class FileHandler {
    public static List<String> getWordsList(String text) {
        return Arrays.asList(text.trim().split("(\\s|\\p{Punct})+"));
    }

    public static List<String> getLinesList(String file) throws IOException {
        String[] lines;

        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            lines = stream.toArray(String[]::new);
        }

        return Arrays.asList(lines);
    }

    public static List<String> getWords(String file) throws IOException {
        StringBuilder builder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(file))) {
            stream.forEach(s -> builder.append(s).append("\n"));
        }

        return FileHandler.getWordsList(builder.toString());
    }
}