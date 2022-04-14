package code.ex2;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.concurrent.*;

public class FoxAlgorithm extends RecursiveTask<Matrix> {
    private int numberOfBlocks;
    private int step;
    Matrix newMatrix;
    Matrix A;
    Matrix B;
    private int[][] xMatrix;
    private final int[][] yMatrix;
  
    public FoxAlgorithm(Matrix A, Matrix B, int blocks) {
        this.newMatrix = new Matrix(A.getXSize(), B.getYSize());
        this.A = A;
        this.B = B;
        this.numberOfBlocks = blocks;
    
        if (!(A.getXSize() == B.getYSize() & A.getXSize() == A.getYSize() & B.getXSize() == B.getYSize())) {
            try {
                throw new Exception("Matrix A and B have different dimensions!");
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        }
    
        numberOfBlocks = Math.min(this.numberOfBlocks, A.getXSize());
        numberOfBlocks = findNearestDivider(this.numberOfBlocks, A.getXSize());
        step = A.getXSize() / this.numberOfBlocks;
        xMatrix = new int[this.numberOfBlocks][this.numberOfBlocks];
        yMatrix = new int[this.numberOfBlocks][this.numberOfBlocks];

        waitBeforeExecute();
    }

    private Matrix cloneBlock(Matrix m, int x, int y, int size) {
        Matrix block = new Matrix(size, size);
        for (int z = 0; z < size; z++) {
            System.arraycopy(m.matrix[z + x], y, block.matrix[z], 0, size);
        }
        return block;
    }
  
    public void waitBeforeExecute() {
        int xStep = 0;
        for (int x = 0; x < numberOfBlocks; x++) {
            int yStep = 0;
            for (int y = 0; y < numberOfBlocks; y++) {
                xMatrix[x][y] = xStep;
                yMatrix[x][y] = yStep;
                yStep += step;
            }
            xStep += this.step;
        }
    }
  
    @Override
    protected Matrix compute() {
        List<RecursiveTask<HashMap<String, Object>>> tasks = new ArrayList<>();
    
        for (int z = 0; z < numberOfBlocks; z++) {
            for (int x = 0; x < numberOfBlocks; x++) {
                for (int y = 0; y < numberOfBlocks; y++) {
                    int xStep1 = xMatrix[x][y];
                    int yStep1 = yMatrix[x][y];
                    int xStep2 = xMatrix[x][(x + z) % numberOfBlocks];
                    int yStep2 = yMatrix[x][(x + z) % numberOfBlocks];
                    int stepI2 = xMatrix[(x + z) % numberOfBlocks][y];
                    int stepJ2 = yMatrix[(x + z) % numberOfBlocks][y];
        
                    ForkJoinFox task = new ForkJoinFox(cloneBlock(A, xStep2, yStep2, step), cloneBlock(B, stepI2, stepJ2, step), xStep1, yStep1);
        
                    tasks.add(task);
                    task.fork();
                }
            }
        }
    
        for (RecursiveTask<HashMap<String, Object>> task : tasks) {
            HashMap<String, Object> map = task.join();
    
            Matrix multipliedBlocks = (Matrix) map.get("multipliedBlocks");
            int xStep = (int) map.get("xStep");
            int yStep = (int) map.get("yStep");
    
            for (int x = 0; x < multipliedBlocks.getXSize(); x++) {
                for (int y = 0; y < multipliedBlocks.getYSize(); y++) {
                    newMatrix.matrix[x + xStep][y + yStep] += multipliedBlocks.matrix[x][y];
                }
            }
        }
    
        return this.newMatrix;
    }

    private int findNearestDivider(int i, int val) {
        int temp = i;
        while (temp > 1) {
            if (val % temp == 0) break;
            if (temp >= i) {
                temp++;
            } else {
                temp--;
            }

            if (temp > Math.sqrt(val)) {
                temp = Math.min(i, val / i) - 1;
            }
        }
        
        if (temp >= i) {
            return temp;
        } else {
            if (temp != 0) {
                return val / temp;
            } else {
                return val;
            }
        }
    }
}