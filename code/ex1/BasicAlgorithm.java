package code.ex1;

import java.util.List;
import java.util.HashMap;

public class BasicAlgorithm {
    public static HashMap<String, Integer> getMap(List<String> words) {
        HashMap<String, Integer> map = new HashMap<>();

        for (String word : words) {
            map.putIfAbsent(word, word.length());
        }

        return map;
    }
}