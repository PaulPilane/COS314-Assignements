import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class KnapsackProblemACO {
private int size;
private int maxWeight;
private Pheromone[] pheromones;
private Random random;
private int[] solutions = null;

private final int NUM_ANTS = 100;
private final int MAX_ITERATIONS = 1000;
private final double ALPHA = 1.0;
private final double BETA = 5.0;
private final double RHO = 0.5;

// To hold the items
private List<Item> items;

    public void processFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            // Allocating some memory
            this.items = new ArrayList<>();

            // Store the size and weight values
            this.size = scanner.nextInt();
            this.maxWeight = scanner.nextInt();

            // Allocate some memory
            this.pheromones = new Pheromone[size];

            // Initialize the pheromones
            for (int i = 0; i < this.size; ++i) {
                this.pheromones[i] = new Pheromone();
            }

            // Now, store the values and weights of items
            for (int i = 0; i < size; i++) {
                double value = scanner.nextDouble();
                double weight = scanner.nextDouble();
                items.add(new Item(value, weight));
            }

            scanner.close();

            // Generate random numbers
            random = new Random();
            random.setSeed(500);

            // Initialize pheromone trails and parameters
            for (int i = 0; i < size; i++) {
                pheromones[i].setLevel(1.0);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void solve() {

        long start = System.nanoTime();
        int[] bestSolution = null;
        double bestValue = 0;

        int iteration = 0;
        // while stopping criteria not met do
        while (iteration < MAX_ITERATIONS) {
            int[][] antSolutions = new int[NUM_ANTS][this.size];
            double[] antValues = new double[NUM_ANTS];

            // for each ant do
            for (int ant = 0; ant < NUM_ANTS; ant++) {
                // Construct a solution using pheromone trails and heuristic information
                antSolutions[ant] = constructSolution();
                // Evaluate the solution
                antValues[ant] = evaluateSolution(antSolutions[ant]);

                // Update best solution
                if (antValues[ant] > bestValue) {
                    bestSolution = antSolutions[ant];
                    bestValue = antValues[ant];
                }
            }
           

            // Update the pheromone trails based on the quality of the solution
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < NUM_ANTS; j++) {
                    if (antSolutions[j] != null && antSolutions[j][i] == 1) {
                        pheromones[i].update(RHO * antValues[j]);
                    }
                }
            }

            // Update the pheromone trails globally
            for (int i = 0; i < size; i++) {
                pheromones[i].update(getDeltaPheromone(i, antSolutions, antValues));
            }

            iteration++;
        }
       
        this.solutions = bestSolution;
        long end = System.nanoTime();

        System.out.println("Best solution: " + Arrays.toString(bestSolution));
        System.out.println("Best value: " + bestValue);

        

        // execution time
        long execution = end - start;
        double elapsedTimeInSecond = (double) execution / 1_000_000_000;

        System.out.println("Execution time: " + elapsedTimeInSecond + " seconds");
    }

    private int[] constructSolution() {
        int[] solution = new int[this.size];
        int totalWeight = 0;

        // Generate solution for one ant
        for (int i = 0; i < this.size; i++) {
            // Calculate probability of selecting item i
            double pheromoneValue = pheromones[i].getLevel();
            double heuristicValue = Math.pow(items.get(i).getValue(), ALPHA) * Math.pow(items.get(i).getWeight(), -BETA);
            double probability = pheromoneValue * heuristicValue;

            // Select item i with probability p or not
            if (random.nextDouble() < probability) {
                solution[i] = 1;
                totalWeight += items.get(i).getWeight();
            } else {
                solution[i] = 0;
            }
        }

        // Check if solution is feasible
        if (totalWeight > maxWeight) {
            return null;
        } else {
            return solution;
        }
    }

    private double evaluateSolution(int[] solution) {
        double totalValue = 0.0;
        if (solution == null)
            return totalValue;

        for (int i = 0; i < size; i++) {
            if (solution[i] == 1) {
                totalValue += items.get(i).getValue();
            }
        }

        return totalValue;
    }

    private double getDeltaPheromone(int item, int[][] antSolutions, double[] antValues) {
        double delta = 0.0;

        if (antSolutions != null) { // Check if antSolutions is not null
            for (int i = 0; i < NUM_ANTS; i++) {
                if (antSolutions[i] != null && antSolutions[i][item] == 1) {
                    delta += antValues[i];
                }
            }
        }

        return delta;
    }

    public void printItemsAndTotalWeight() {
        double totalWeight = 0.0;
        System.out.println("Selected items in the knapsack:");
        for (int i = 0; i < size; i++) {
            if (this.solutions[i] == 1) {
                Item item = items.get(i);
                System.out.println("- Item " + (i + 1) + ": value = " + item.getValue() + ", weight = " + item.getWeight());
                totalWeight += item.getWeight();
            }
        }
        System.out.println("Total weight of selected items: " + totalWeight);
    }
}