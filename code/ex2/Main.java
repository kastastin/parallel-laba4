package code.ex2;
import java.util.concurrent.ForkJoinPool;

public class Main {
    public static void main(String[] args) {
        int xSize = 1000;
        int ySize = 1000;

        Matrix A = new Matrix(xSize, ySize);
        A.fillMatrixRandomly();
        Matrix B = new Matrix(xSize, ySize);
        B.fillMatrixRandomly();


        CommonAlgorithm common = new CommonAlgorithm(A, B);
        long timeCommon = System.nanoTime();
        Matrix multipliedMatrix = common.multiply();
        ////////////////////////////////
        multipliedMatrix.displayMatrix();
        ///////////////////////////////
        timeCommon = System.nanoTime() - timeCommon;
        
        long timeFox = System.nanoTime();
        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        multipliedMatrix = pool.invoke(new FoxAlgorithm(A, B, 6));
        timeFox = System.nanoTime() - timeFox;

        System.out.printf("\nCommon Algorithm: %d %s", timeCommon / 1000000, "ms");
        System.out.printf("\nFox Algorithm: %d %s", timeFox / 1000000, "ms");
        System.out.printf("\nSpeed Up (Comon / ForkJoinFox): %f", (double) timeCommon / timeFox);

        int resultFoxFromLaba2 = 379;
        System.out.printf("\n\nComparing with past laba (Fox: %d ms)", resultFoxFromLaba2);
        System.out.printf("\nSpeedUp (Fox 2 Laba / ForkJoinFox): %.2f\n\n", (double) resultFoxFromLaba2 / (timeFox / 1000000));
    }
}