package code.ex4;

import java.io.File;
import java.util.List;
import java.util.Objects;
import java.io.IOException;
import java.util.LinkedList;

public class Folder {
    private List<Folder> subfolder;
    private List<Document> documents;

    Folder(List<Folder> subfolders, List<Document> documents) {
        this.subfolder = subfolders;
        this.documents = documents;
    }

    static Folder getFolderContent(File path) throws IOException {
        List<Folder> subfolders = new LinkedList<>();
        List<Document> documents = new LinkedList<>();

        for (File file : Objects.requireNonNull(path.listFiles())) {
            if (!file.isDirectory()) {
                documents.add(Document.getDocumentContent(file));
            } else {
                subfolders.add(Folder.getFolderContent(file));
            }
        }

        return new Folder(subfolders, documents);
    }

    List<Folder> getSubfolders() { return this.subfolder; }
    List<Document> getDocuments() { return this.documents; }
}