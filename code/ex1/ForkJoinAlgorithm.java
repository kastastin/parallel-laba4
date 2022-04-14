package code.ex1;

import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinTask;

public class ForkJoinAlgorithm extends RecursiveTask<HashMap<String, Integer>> {
    private List<String> words;
    private static int threshold = 5000;
    private HashMap<String, Integer> map = new HashMap<>();

    public ForkJoinAlgorithm(List<String> words) {
        this.words = words;
    }

    private HashMap<String, Integer> getMap(List<String> words) {
        for (String word : words) {
            map.putIfAbsent(word, word.length());
        }

        return map;
    }

    private Collection<ForkJoinAlgorithm> generateSubtask() {
        List<ForkJoinAlgorithm> tasks = new ArrayList<>();
        tasks.add(new ForkJoinAlgorithm(words.subList(0, words.size() / 2)));
        tasks.add(new ForkJoinAlgorithm(words.subList(words.size() / 2, words.size())));

        return tasks;
    }

    @Override
    protected HashMap<String, Integer> compute() {
        if (words.size() < threshold) {
            return getMap(words);
        } else {
            ForkJoinTask.invokeAll(generateSubtask())
                .stream()
                .map(ForkJoinTask::join)
                .flatMap(map -> map.entrySet().stream())
                .forEach(word -> map.putIfAbsent(word.getKey(), word.getValue()));

            return map;
        }
    }
}