import java.util.ArrayList;
import java.util.List;

public class Ant {

    private List<Item> items;
    private int knapsackCapacity;
    private List<Boolean> visited;
    private List<Double> pheromoneTrail;
    List<Pheromone> pheromoneTrails;

    public Ant(List<Item> items, int knapsackCapacity, List<Pheromone> pheromoneTrails) {
        this.items = items;
        this.knapsackCapacity = knapsackCapacity;
        this.pheromoneTrails = pheromoneTrails;
        this.visited = new ArrayList<>();
        this.pheromoneTrail = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            visited.add(false);
            pheromoneTrail.add(1.0);
        }
    }

    public void explore() {
        // Start at the first item.
        visited.set(0, true);

        // Repeat until all items have been visited.
        while (!visited.contains(false)) {
            // Choose the next item to visit.
            int nextItemIndex = chooseNextItem();

            // Visit the next item.
            visited.set(nextItemIndex, true);

            // Update the pheromone trail.
            for (int i = 0; i < items.size(); i++) {
                if (visited.get(i)) {
                    pheromoneTrail.set(i, pheromoneTrail.get(i) * AntColonyOptimization.ALPHA + AntColonyOptimization.BETA * pheromoneTrails.get(i));
                }
            }
        }
    }

    private int chooseNextItem() {
        // Create a list of all possible next items.
        List<Integer> nextItems = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            if (!visited.get(i)) {
                nextItems.add(i);
            }
        }

        // Choose the next item using a probability distribution based on the pheromone trails.
        double[] probabilities = new double[nextItems.size()];
        for (int i = 0; i < probabilities.length; i++) {
            probabilities[i] = pheromoneTrails.get(nextItems.get(i)).get(nextItems.get(i));
        }
        double totalProbability = 0.0;
        for (double probability : probabilities) {
            totalProbability += probability;
        }
        double randomNumber = Math.random() * totalProbability;
        int nextItemIndex = -1;
        for (int i = 0; i < probabilities.length; i++) {
            randomNumber -= probabilities[i];
            if (randomNumber < 0) {
                nextItemIndex = nextItems.get(i);
                break;
            }
        }

        return nextItemIndex;
    }

    private double getPheromoneTrail(int fromIndex, int toIndex) {
        return pheromoneTrails.get(fromIndex).get(toIndex);
    }
}
// Compare this snippet from ACO_v2\KnapsackProblemACO.java: