package code.ex3;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String path = "txts/ex2";

        WordSet words = new WordSet();
        Folder folder = Folder.getFolderContent(new File(path));

        words.displayCommonWords(folder);
    }
}