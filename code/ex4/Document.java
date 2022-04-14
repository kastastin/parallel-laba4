package code.ex4;

import java.io.File;
import java.util.List;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.io.BufferedReader;

public class Document {
    public final String name;
    private final List<String> rows;

    Document(String name, List<String> rows) {
        this.name = name;
        this.rows = rows;
    }

    static Document getDocumentContent(File file) throws IOException {
        List<String> rows = new LinkedList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String row = reader.readLine();
            while (row != null) {
                rows.add(row);
                row = reader.readLine();
            }
        }
        
        return new Document(file.getAbsolutePath(), rows);
    }

    List<String> getLines() { return this.rows; }
}