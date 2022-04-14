package code.ex3;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public class WordSet {
    private int nThread = Runtime.getRuntime().availableProcessors();

    public static Set<String> getWords(Document doc) {
        Set<String> words = new HashSet<>();

        for (String row : doc.getLines()) {
            words.addAll(Arrays.asList(getRegexWords(row)));
        }

        return words;
    }

    private ForkJoinPool pool = new ForkJoinPool(nThread);

    public static String[] getRegexWords(String row) {
        return row.trim().split("(\\s|\\p{Punct})+");
    }

    public void displayCommonWords(Folder folder) {
        System.out.printf("\nCommon words: \n%s\n\n", pool.invoke(new FolderTask(folder)));
    }
}