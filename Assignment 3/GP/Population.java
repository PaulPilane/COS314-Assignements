import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Population {
    private List<Individual> individuals;

    public Population() {
        this.individuals = new ArrayList<>();
    }

    public void initializePopulation(int populationSize, int maxDepth) {
        Random random = new Random();
        for (int i = 0; i < populationSize; i++) {
            Individual individual = new Individual();
            individual.initializeRandomTree(maxDepth, random);
            individuals.add(individual);
        }
    }

    public Individual selectIndividual(Random random) {
        int tournamentSize = 5;
        Individual bestIndividual = null;

        for (int i = 0; i < tournamentSize; i++) {
            int randomIndex = random.nextInt(individuals.size());
            Individual individual = individuals.get(randomIndex);

            if (bestIndividual == null || individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
        }

        return bestIndividual;
    }

    public void evolvePopulation(double crossoverRate, double mutationRate, Dataset dataset) throws CloneNotSupportedException  {
        List<Individual> newIndividuals = new ArrayList<>();
        Random random = new Random();

        // Elitism: Keep the best individual from the previous generation
        Individual bestIndividual = getBestIndividual();
        newIndividuals.add(bestIndividual);

        while (newIndividuals.size() < individuals.size()) {
            Individual parent1 = selectIndividual(random);
            Individual parent2 = selectIndividual(random);

            // Perform crossover operation
            Individual offspring = crossover(parent1, parent2, crossoverRate, random);

            // Perform mutation operation
            mutation(offspring, mutationRate, random);

            offspring.evaluateFitness(dataset);
            newIndividuals.add(offspring);
        }

        individuals = newIndividuals;
    }

    public Individual getBestIndividual() {
        Individual bestIndividual = individuals.get(0);

        for (int i = 1; i < individuals.size(); i++) {
            Individual individual = individuals.get(i);
            if (individual.getFitness() > bestIndividual.getFitness()) {
                bestIndividual = individual;
            }
        }

        return bestIndividual;
    }

    private Individual crossover(Individual parent1, Individual parent2, double crossoverRate, Random random) throws CloneNotSupportedException {
        if (random.nextDouble() < crossoverRate) {
            DecisionTree parent1Tree = parent1.getDecisionTree();
            DecisionTree parent2Tree = parent2.getDecisionTree();
            DecisionTree offspringTree = parent1Tree.crossover(parent2Tree, random);
            return new Individual(offspringTree);
        } else {
            return parent1;
        }
    }

    private void mutation(Individual individual, double mutationRate, Random random) {
        if (random.nextDouble() < mutationRate) {
            individual.getDecisionTree().mutate(random);
        }
    }
}
