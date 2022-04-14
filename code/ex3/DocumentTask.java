package code.ex3;

import java.util.Set;
import java.util.concurrent.RecursiveTask;

public class DocumentTask extends RecursiveTask<Set<String>> {
    private Document document;

    DocumentTask(Document doc) {
        super();
        this.document = doc;
    }

    @Override
    protected Set<String> compute() {
        return WordSet.getWords(document);
    }
}