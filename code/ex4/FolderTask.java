package code.ex4;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderTask extends RecursiveTask<List<String>> {
    private Folder folder;
    private List<String> word;

    FolderTask(Folder folder, List<String> word) {
        super();
        this.folder = folder;
        this.word = word;
    }

    @Override
    protected List<String> compute() {
        List<String> result = new ArrayList<>();
        List<RecursiveTask<List<String>>> forks = new LinkedList<>();

        for (Folder subfolder : folder.getSubfolders()) {
            FolderTask task = new FolderTask(subfolder, word);
            forks.add(task);
            task.fork();
        }

        for (Document doc : folder.getDocuments()) {
            DocumentTask task = new DocumentTask(doc, word);
            forks.add(task);
            task.fork();
        }

        for (RecursiveTask<List<String>> task : forks) {
            List<String> taskRes = task.join();
            if (taskRes == null) continue;
            result.addAll(taskRes);
        }

        return result;
    }
}