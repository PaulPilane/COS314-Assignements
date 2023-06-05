import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GPAlgorithm {
    private int populationSize;
    private int maxGenerations;
    private double mutationRate;
    private double crossoverRate;

    public GPAlgorithm(int populationSize, int maxGenerations, double mutationRate, double crossoverRate) {
        this.populationSize = populationSize;
        this.maxGenerations = maxGenerations;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
    }

    public DecisionTree evolveDecisionTree(List<List<String>> data) {
        // Create initial population
        List<Individual> population = createInitialPopulation(this.populationSize, new Random());

        // Evaluate the fitness of individuals in the initial population
        evaluateFitness(population, data);

        // Evolution loop
        for (int generation = 0; generation < maxGenerations; generation++) {
            System.out.println("Generation: " + (generation + 1));

            // Select parents for reproduction (tournament selection)
            List<Individual> parents = selectParents(population);

            // Create offspring through crossover and mutation
            List<Individual> offspring = createOffspring(parents);

            // Evaluate the fitness of offspring
            evaluateFitness(offspring, data);

            // Select individuals for the next generation (elitism)
            List<Individual> nextGeneration = selectNextGeneration(population, offspring);

            // Replace the current population with the next generation
            population = nextGeneration;

            // Print the best individual and average fitness of the current generation
            printGenerationStats(population);
        }

        // Find and return the best individual in the final population
        return findBestIndividual(population);
    }

    private List<Individual> createInitialPopulation(int maxDepth, Random random) {
        List<Individual> population = new ArrayList<>();
    
        for (int i = 0; i < populationSize; i++) {
            DecisionTree decisionTree = new DecisionTree();
            decisionTree.initializeRandomTree(maxDepth, random);
            Individual individual = new Individual(decisionTree);
            population.add(individual);
        }
    
        return population;
    }

    private void evaluateFitness(List<Individual> population, List<List<String>> data) {
        for (Individual individual : population) {
            double fitness = calculateFitness(individual, data);
            individual.setFitness(fitness);
        }
    }

    private double calculateFitness(Individual individual, List<List<String>> data) {
        // Implement the fitness evaluation logic for the decision tree
        // You can use metrics such as accuracy, precision, recall, or F1-score
        // Here, I'll simply return a random fitness value for demonstration purposes

        Random random = new Random();
        return random.nextDouble();
    }

    private List<Individual> selectParents(List<Individual> population) {
        // Implement the parent selection method (e.g., tournament selection)
        // Select individuals from the population to be parents for reproduction
        // Here, I'll simply return a random subset of the population for demonstration purposes

        Random random = new Random();
        List<Individual> parents = new ArrayList<>();

        while (parents.size() < populationSize / 2) {
            int randomIndex = random.nextInt(populationSize);
            parents.add(population.get(randomIndex));
        }

        return parents;
    }

    private List<Individual> createOffspring(List<Individual> parents) {
        List<Individual> offspring = new ArrayList<>();

        while (offspring.size() < populationSize) {
            Individual parent1 = selectRandomParent(parents);
            Individual parent2 = selectRandomParent(parents);

            Individual child = crossover(parent1, parent2);
            mutate(child);

            offspring.add(child);
        }

        return offspring;
    }

    private Individual selectRandomParent(List<Individual> parents) {
        Random random = new Random();
        int randomIndex = random.nextInt(parents.size());
        return parents.get(randomIndex);
    }

    private Individual crossover(Individual parent1, Individual parent2) {
        // Implement the crossover operation (e.g., subtree crossover)
        // Combine genetic material from parent1 and parent2 to create a child individual
        // Here, I'll simply return a copy of parent1 for demonstration purposes

        return parent1;
    }

    private void mutate(Individual individual) {
        // Implement the mutation operation (e.g., subtree mutation)
        // Mutate the genetic material of the individual
        // Here, I'll simply do nothing for demonstration purposes
    }

    private List<Individual> selectNextGeneration(List<Individual> population, List<Individual> offspring) {
        // Implement the selection method for the next generation (e.g., elitism)
        // Select individuals from the current population and offspring to form the next generation
        // Here, I'll simply return the offspring for demonstration purposes

        return offspring;
    }

    private void printGenerationStats(List<Individual> population) {
        double totalFitness = 0.0;

        for (Individual individual : population) {
            totalFitness += individual.getFitness();
        }

        double averageFitness = totalFitness / population.size();
        System.out.println("Best Fitness: " + findBestFitness(population));
        System.out.println("Average Fitness: " + averageFitness);
    }

    private double findBestFitness(List<Individual> population) {
        double bestFitness = Double.MIN_VALUE;

        for (Individual individual : population) {
            if (individual.getFitness() > bestFitness) {
                bestFitness = individual.getFitness();
            }
        }

        return bestFitness;
    }

    private DecisionTree findBestIndividual(List<Individual> population) {
        Individual bestIndividual = population.get(0);

        for (Individual individual : population) {
            if (individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
        }

        return bestIndividual.getDecisionTree();
    }
}
