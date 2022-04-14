package code.ex4;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class WordSet {
    private int nThread = Runtime.getRuntime().availableProcessors();

    public static Long getAllCounts(List<String> keys, Document doc) {
        long count = 0;

        for (String row : doc.getLines()) {
            for (String word : getRegexWords(row)) {
                if (keys.contains(word)) count = count + 1;
            }
        }

        return count;
    }

    public Long getThreadCounts(Folder folder, List<String> keys) {
        long count = 0;

        for (Folder subfolder : folder.getSubfolders()) {
            count += getThreadCounts(subfolder, keys);
        }

        for (Document doc : folder.getDocuments()) {
            count += getAllCounts(keys, doc);
        }

        return count;
    }

    private final ForkJoinPool pool = new ForkJoinPool(nThread);

    public List<String> getPaths(Folder folder, List<String> searchedWords) {
        return pool.invoke(new FolderTask(folder, searchedWords));
    }

    public static String[] getRegexWords(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public void displayResult(WordSet words, Folder path, List<String> keys) {
        for (String doc : words.getPaths(path, keys))
            System.out.println(doc);
    }
}