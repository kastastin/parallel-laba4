package code.ex4;

import java.util.List;
import java.util.Collections;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask<List<String>> {
    private Document document;
    private List<String> word;

    DocumentTask(Document doc, List<String> word) {
        super();
        this.word = word;
        this.document = doc;
    }

    @Override
    protected List<String> compute() {
        if (WordSet.getAllCounts(word, document) == 0) {
            return null;
        } else {
            return Collections.singletonList(document.name);
        }
    }
}