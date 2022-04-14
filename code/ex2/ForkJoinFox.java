package code.ex2;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

public class ForkJoinFox extends RecursiveTask<HashMap<String, Object>> {
    private Matrix A;
    private Matrix B;
    private int xStep;
    private int yStep;

    public ForkJoinFox(Matrix A, Matrix B, int x, int y) {
        this.A = A;
        this.B = B;
        this.xStep = x;
        this.yStep = y;
    }

    private Matrix multiplyBlocks() {
        Matrix multipliedBlocks = new Matrix(A.getYSize(), B.getXSize());
        for (int x = 0; x < A.getXSize(); x++) {
            for (int y = 0; y < B.getYSize(); y++) {
                for (int z = 0; z < A.getYSize(); z++) {
                    multipliedBlocks.matrix[x][y] += A.matrix[x][z] * B.matrix[z][y];
                }
            }
        }
        return multipliedBlocks;
    }

    @Override
    protected HashMap<String, Object> compute() {
        Matrix multipliedBlocks = multiplyBlocks();

        HashMap<String, Object> map = new HashMap<>();
        map.put("multipliedBlocks", multipliedBlocks);
        map.put("xStep", xStep);
        map.put("yStep", yStep);

        return map;
    }
}