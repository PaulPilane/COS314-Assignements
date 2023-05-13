import java.util.ArrayList;
import java.util.List;

public class AntColonyOptimization {

    private static final int MAX_ITERATIONS = 100;
    public static final double ALPHA = 1;
    public static final double BETA = 5;
    public static final double RHO = 0.1;

    private List<Item> items;
    private int knapsackCapacity;
    // List<List<Double>> pheromoneTrails;

    public AntColonyOptimization(List<Item> items, int knapsackCapacity) {
        this.items = items;
        this.knapsackCapacity = knapsackCapacity; 
    }

    public List<Item> solve() {
        // Initialize the pheromone trails to 1.
        List<PheromoneTrail> pheromoneTrails = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            pheromoneTrails.add(new PheromoneTrail(items.size()));
        }

        // Repeat for MAX_ITERATIONS.
        for (int i = 0; i < MAX_ITERATIONS; i++) {
            // Create a new colony of ants.
            List<Ant> ants = new ArrayList<>();
            for (int j = 0; j < items.size(); j++) {
                ants.add(new Ant(items, knapsackCapacity, pheromoneTrails));
            }

            // Let the ants explore the search space.
            for (Ant ant : ants) {
                ant.explore();
            }

            // Update the pheromone trails.
            for (PheromoneTrail pheromoneTrail : pheromoneTrails) {
                for (int j = 0; j < pheromoneTrails.size(); j++) {
                    pheromoneTrail.set(j, (1 - RHO) * pheromoneTrails.get(j) + RHO * pheromoneTrail.get(j, j));
                }
            }
        }

        // Find the best solution.
        List<Item> bestSolution = null;
        double bestProfit = 0;
        for (List<Item> solution : pheromoneTrails) {
            double profit = 0;
            for (Item item : solution) {
                profit += item.profit;
            }
            if (profit > bestProfit) {
                bestSolution = solution;
                bestProfit = profit;
            }
        }

        return bestSolution;
    }
}