import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
public class KnapsackProblemACO {
private int size;
private KnapsackProblem problem;
private Ant[] ants;
private Pheromone pheromone;
private int maxWeight;
private final int NUM_ANTS = 100;
private final int MAX_ITERATIONS = 500;
private final double INITIAL_PHEROMONE = 1.0;
private final double EVAPORATION_RATE = 0.5;
private Random random;

// To hold the items
private List<Item> items;

    public void processFile(String filename) {
        try {
            Scanner scanner = new Scanner(new File(filename));

            // Allocating some memory
            this.items = new ArrayList<>();
            this.random = new Random();

            // Store the size and weight values
            this.size = scanner.nextInt();
            this.maxWeight = scanner.nextInt();

            // Now, store the values and weights of items
            for (int i = 0; i < size; ++i) {
                double value = scanner.nextDouble();
                double weight = scanner.nextDouble();
                items.add(new Item(value, weight));
            }

            scanner.close();

            // Initial what-what
            this.problem = new KnapsackProblem(items.toArray(new Item[items.size()]), maxWeight);
            this.pheromone = new Pheromone(problem.getNumItems(), 2, INITIAL_PHEROMONE, EVAPORATION_RATE);
            this.ants = new Ant[NUM_ANTS];


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        // Start iterations

        int bestValue = Integer.MIN_VALUE;
        int[] bestSolution = null;
        long start = System.nanoTime();
        for(int iteration = 0; iteration < MAX_ITERATIONS; ++iteration) {
            // Assign ants to solutions
            for (int ant = 0; ant < NUM_ANTS; ++ant) {
                ants[ant] = new Ant(problem, pheromone, random.nextLong());
                ants[ant].constructSolution();
            }

            // Update best solution if a better one is found
            for (int ant = 0; ant < NUM_ANTS; ++ant) {
                int currentValue = ants[ant].getSolutionValue();
                if (currentValue > bestValue) {
                    bestValue = currentValue;
                    bestSolution = ants[ant].getSolution().clone();
                }
            }

            // Update pheromones
            pheromone.evaporate();
            for (int i = 0; i < problem.getNumItems(); ++i) {
                double sumDeltaPheromones = 0;
                for (int ant = 0; ant < NUM_ANTS; ++ant) {
                    if (ants[ant].getSolution()[i] == 1) {
                        sumDeltaPheromones += 1.0 / ants[ant].getSolutionValue();
                    }
                }

                pheromone.deposit(i, 0, sumDeltaPheromones);
                pheromone.deposit(i, 1, sumDeltaPheromones);
            }

        }

        long end = System.nanoTime();

        // execution time
        long execution = end - start;
        double elapsedTimeInSecond = (double) execution / 1_000_000_000;
    
        System.out.println("Execution time: " + elapsedTimeInSecond + " seconds");

        // Print best solution
        System.out.print("Selected Items: [");
        
        for (int i = 0; i < bestSolution.length; ++i) {
                if(i < (bestSolution.length -1))
                    System.out.print(bestSolution[i] + ", ");
                else {
                    System.out.println(bestSolution[i] + "]");
                }
        }
        System.out.println("Best solution: " + bestValue);
        System.out.print("Items in knapsack: ");
        for (int i = 0; i < bestSolution.length; ++i) {
            if (bestSolution[i] == 1) {
                System.out.print(i + " ");
            }
        }
        System.out.println("\n");
    }
}