import java.util.ArrayList;
import java.util.List;
import java.util.Random;
public class Individual implements Comparable<Individual> {

    private List<Integer> genes;
    private double fitness;

    public Individual(int numGenes) {
        genes = new ArrayList<>(numGenes);
        Random random = new Random();
        for (int i = 0; i < numGenes; i++) {
            genes.add(random.nextInt(2));
        }
    }

    public List<Integer> getGenes() {
        return genes;
    }

    public int getGene(int index) {
        return genes.get(index);
    }

    public void setGene(int index, int value) {
        genes.set(index, value);
    }

    public int size() {
        return genes.size();
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public Individual crossover(Individual other) {
        Individual offspring = new Individual(genes.size());
        Random random = new Random();
        int crossoverPoint = random.nextInt(genes.size());
        for (int i = 0; i < genes.size(); i++) {
            if (i < crossoverPoint) {
                offspring.setGene(i, genes.get(i));
            } else {
                offspring.setGene(i, other.getGene(i));
            }
        }
        return offspring;
    }

    public void mutate() {
        Random random = new Random();
        int mutationPoint = random.nextInt(genes.size());
        genes.set(mutationPoint, 1 - genes.get(mutationPoint));
    }

    @Override
    public int compareTo(Individual other) {
        return Double.compare(fitness, other.fitness);
    }
    
}