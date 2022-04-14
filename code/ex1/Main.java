package code.ex1;

import java.util.List;
import java.util.HashMap;
import java.io.IOException;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> words = FileHandler.getWords("txts/ex1.txt");
        
        // ForkJoinFramework Algorithm TIME
        long time = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        HashMap<String, Integer> map = pool.submit(new ForkJoinAlgorithm(words)).join();
        long timeForkJoinAlgorithm = System.nanoTime() - time;

        // Basic Algorithm TIME
        time = System.nanoTime();
        map = BasicAlgorithm.getMap(words);
        long timeBasicAlgorithm = System.nanoTime() - time;

        System.out.printf("\nForkJoinFramework Algorithm: %d %s\n", timeForkJoinAlgorithm / 1000000, "ms");
        System.out.printf("Basic Algorithm: %d %s\n", timeBasicAlgorithm / 1000000, "ms");
        System.out.printf("Spead Up = %.3f\n", (double) timeBasicAlgorithm / timeForkJoinAlgorithm);
        
        System.out.printf("\nWords: %d\n", words.size());
        System.out.printf("Unique words: %d\n", map.keySet().size());
        System.out.printf("Average word length: %.2f\n", map.values().stream().mapToDouble(i -> i).sum() / map.values().stream().mapToDouble(i -> i).count());
        System.out.println();
    }
}