package code.ex4;

import java.io.File;
import java.util.List;
import java.util.Arrays;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> keys = Arrays.asList("algorithm", "java");
        Folder path = Folder.getFolderContent(new File("txts"));

        System.out.printf("\nPaths to files where keywords were found:\n");
        WordSet words = new WordSet();
        words.displayResult(words, path, keys);
        
        System.out.println();
    }
}