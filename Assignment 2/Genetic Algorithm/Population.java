import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Population {

    private List<Individual> individuals;

    public Population(int size, int numGenes) {
        individuals = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            individuals.add(new Individual(numGenes));
        }
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    // Tournament Selection
    public Population selectParents() {
        Population parents = new Population(individuals.size(), individuals.get(0).size());
        Random random = new Random();
        for (int i = 0; i < individuals.size(); i++) {
            parents.getIndividuals().set(i, tournamentSelection(random));
        }
        return parents;
    }

    private Individual tournamentSelection(Random random) {
        Individual[] candidates = new Individual[4];
        for (int i = 0; i < 4; i++) {
            candidates[i] = individuals.get(random.nextInt(individuals.size()));
        }
        Individual winner = candidates[0];
        for (int i = 1; i < 4; i++) {
            if (candidates[i].getFitness() > winner.getFitness()) {
                winner = candidates[i];
            }
        }
        return winner;
    }

    public Population crossover() {
        Population offspring = new Population(individuals.size(), individuals.get(0).size());
        Random random = new Random();
        for (int i = 0; i < individuals.size(); i++) {
            Individual parent1 = individuals.get(i);
            Individual parent2 = individuals.get(random.nextInt(individuals.size()));
            offspring.getIndividuals().set(i, parent1.crossover(parent2));
        }
        return offspring;
    }

    public void mutate(double mutationRate) {
        Random random = new Random();
        for (Individual individual : individuals) {
            if (random.nextDouble() < mutationRate) {
                individual.mutate();
            }
        }
    }

    public Population selectSurvivors(Population offspring) {
        Population nextGeneration = new Population(individuals.size(), individuals.get(0).size());
        List<Individual> combined = new ArrayList<>(individuals);
        combined.addAll(offspring.getIndividuals());
        Collections.sort(combined, Collections.reverseOrder());
        nextGeneration.getIndividuals().addAll(combined.subList(0, individuals.size()));
        return nextGeneration;
    }

    public Individual getBestIndividual() {
        return Collections.max(individuals);
    }
}