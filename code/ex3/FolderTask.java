package code.ex3;

import java.util.*;
import java.util.concurrent.RecursiveTask;

public class FolderTask extends RecursiveTask<Set<String>> {
    private Folder folder;

    FolderTask(Folder folder) {
        super();
        this.folder = folder;
    }

    @Override
    protected Set<String> compute() {
        Set<String> result;
        List<RecursiveTask<Set<String>>> forks = new LinkedList<>();

        for (Folder subfolder : folder.getSubfolders()) {
            FolderTask task = new FolderTask(subfolder);
            forks.add(task);
            task.fork();
        }

        for (Document doc : folder.getDocuments()) {
            DocumentTask task = new DocumentTask(doc);
            forks.add(task);
            task.fork();
        }

        if (forks.size() == 0) {
            return new HashSet<>();
        } 

        result = forks.get(0).join();
        forks.remove(0);
        
        for (RecursiveTask<Set<String>> task : forks) {
            result.retainAll(task.join());
        }

        return result;
    }
}