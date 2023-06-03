import java.util.Random;

public class Ant {
    static final double ALPHA = 1;
    static final double BETA = 5;
    private static final double Q0 = 0.9;
    private int[] solution;
    private KnapsackProblem problem;
    private Pheromone pheromone;
    private Random random;

    public Ant(KnapsackProblem problem, Pheromone pheromone, long seed) {
        this.problem = problem;
        this.pheromone = pheromone;
        this.solution = new int[problem.getNumItems()];
        this.random = new Random(seed);
    }

    public void constructSolution() {
        int totalWeight = 0;

        for (int i = 0; i < problem.getNumItems(); i++) {
            double r = random.nextDouble();
            if (r < Q0) {
                // Select the item with the highest pheromone
                if (pheromone.get(i, 0) > pheromone.get(i, 1) && totalWeight + problem.getWeight(i) <= problem.getCapacity()) {
                    solution[i] = 1;
                    totalWeight += problem.getWeight(i);
                } else {
                    solution[i] = 0;
                }
            } else {
                // Select item based on probability
                double probability = pheromone.get(i, 0) / (pheromone.get(i, 0) + pheromone.get(i, 1));
                if (random.nextDouble() <= probability && totalWeight + problem.getWeight(i) <= problem.getCapacity()) {
                    solution[i] = 1;
                    totalWeight += problem.getWeight(i);
                } else {
                    solution[i] = 0;
                }
            }
        }
    }

    public int[] getSolution() {
        return solution;
    }

    public int getSolutionValue() {
        int value = 0;
        for (int i = 0; i < problem.getNumItems(); i++) {
            if (solution[i] == 1) {
                value += problem.getValue(i);
            }
        }
        return value;
    }

    public void setSolution(int[] solution) {
        this.solution = solution;
    }

}