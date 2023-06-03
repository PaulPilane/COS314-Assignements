public class Pheromone {
    private double[][] matrix;
    private double evaporationRate;

    public Pheromone(int numRows, int numCols, double initialPheromone, double evaporationRate) {
        this.matrix = new double[numRows][numCols];
        this.evaporationRate = evaporationRate;

        for (int i = 0; i < numRows; ++i) {
            for (int j = 0; j < numCols; ++j) {
                matrix[i][j] = initialPheromone;
            }
        }
    }

    public double get(int row, int col) {
        return matrix[row][col];
    }

    public void set(int row, int col, double value) {
        matrix[row][col] = value;
    }

    public void evaporate() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] *= (1 - evaporationRate);
            }
        }
    }

    public void deposit(int row, int col, double amount) {
        matrix[row][col] += amount;
    }
}