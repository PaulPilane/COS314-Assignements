import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class KnapsackGA {

    // Constants
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 12;
    private static final double MUTATION_RATE = 0.05;
    private final double CROSSSOVER_RATE = 0.6;

    // Instance variables
    private List<Item> items;
    private int knapsackCapacity;

    public void processFile(String fileName) {

        // Read input file and create list of items
        items = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            int numItems = scanner.nextInt();
            this.knapsackCapacity = scanner.nextInt();

            for (int i = 0; i < numItems; ++i) {
                double value = scanner.nextDouble();
                double weight = scanner.nextDouble();
                this.items.add(new Item(value, weight));
            }
            scanner.close();
            
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());

            // Shut down the JVM when there is file read error 
            System.exit(1);
        }

    }

    public void solve(boolean debug) {

        // Start counting
        long start = System.nanoTime();
        // Initialize population
        Population population = new Population(POPULATION_SIZE, items.size());
        // Evolve population for a fixed number of generations
        for (int i = 0; i < MAX_GENERATIONS; i++) {

            // Evaluate fitness of each individual in the population
            for (Individual individual : population.getIndividuals()) {
                double fitness = evaluateFitness(individual);
                individual.setFitness(fitness);
            }

            // Select parents for mating
            Population parents = population.selectParents();

            // Create offspring by crossover and mutation
            Population offspring = parents.crossover(CROSSSOVER_RATE);
            offspring.mutate(MUTATION_RATE);
            
            // Evaluate fitness of offspring
            for (Individual individual : offspring.getIndividuals()) {
                double fitness = evaluateFitness(individual);
                individual.setFitness(fitness);
            }
            // Select survivors for next generation
            population = population.selectSurvivors(offspring);

            // Print best solution so far
            Individual bestIndividual = population.getBestIndividual();
            System.out.printf("Generation %d: Best fitness = %.2f\n", i+1, bestIndividual.getFitness());
        }
        long end = System.nanoTime();
        // Print final solution
        Individual bestIndividual = population.getBestIndividual();
        System.out.printf("Final solution: Fitness = %.2f\n", bestIndividual.getFitness());

       System.out.println("Best Solution: " + bestIndividual.getGenes());

        // end time
        

        // execution time
        long execution = end - start;
        double executionInSeconds = (double) execution/1_000_000_000;

        //The debug variable is true when the added weights want to be displayed
        if(!debug)
            return;
        
        // Print the weight that is added to the knapsack
        printSelectedItems(population.getBestIndividual().getGenes());

        // print the time elapsed
        System.out.println("Execution time: " + executionInSeconds + " seconds");
        
    }

    private double evaluateFitness(Individual individual) {
        // Calculate the total value and weight of the items in the knapsack
        double totalValue = 0;
        double totalWeight = 0;
        for (int i = 0; i < individual.size(); ++i) {
            if (individual.getGene(i) == 1) {
                totalValue += items.get(i).getValue();
                totalWeight += items.get(i).getWeight();
            }
        }
        
        // Calculate the penalty factor using a sigmoid function
        double penaltyFactor = 1.0 / (1.0 + Math.exp(-1 * (knapsackCapacity - totalWeight)));
        
        // Return the total value of the knapsack multiplied by the penalty factor as the fitness score
        return totalValue * penaltyFactor;
    }
    
 // Debugging purposes
    public void printSelectedItems(List<Integer> genes) {
        double totalWeight = 0;
        System.out.println("Selected items: ");
        for (int i = 0; i < genes.size(); i++) {
            if (genes.get(i) == 1) {
                Item item = items.get(i);
                System.out.println("- Item " + (i + 1) + ": value = " + item.getValue() + ", weight = " + item.getWeight());
                totalWeight += item.getWeight();
            }
        }

        System.out.println("Total weight of selected items: " + totalWeight);
    }
}