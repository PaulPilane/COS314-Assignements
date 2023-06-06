import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ILS {

    private static final int MAX_ITERATIONS = 100;
    private double binCapacity;
    private List<Double> items;

    public ILS(List<Double> items, double binCapacity) {
        this.binCapacity = binCapacity;
        this.items = items;
    }

    public List<Bin> generateInitialSolution() {
        // Sort items in non-increasing order
        Collections.sort(items, Collections.reverseOrder());

        List<Bin> bins = new ArrayList<>();
        bins.add(new Bin(binCapacity));

        // Place items in the first bin where they fit
        for (double item : items) {
            boolean itemPlaced = false;

            for (Bin bin : bins) {
                if (bin.add(item)) {
                    itemPlaced = true;
                    break;
                }
            }

            if (!itemPlaced) {
                Bin newBin = new Bin(binCapacity);
                newBin.add(item);
                bins.add(newBin);
            }
        }

        return bins;
    }

    public List<Bin> localSearch(List<Bin> bins) {
        List<Bin> currentSolution = new ArrayList<>(bins);
        double currentFitness = calculateFitness(currentSolution);

        // Perform perturbation
        perturbation(currentSolution, 0.1);

        // Perform local search
        List<Bin> bestSolution = new ArrayList<>(currentSolution);
        double bestFitness = currentFitness;

        boolean improved;
        int numIterations = 0;
    do {
        improved = false;

        for (int i = 0; i < currentSolution.size(); i++) {
            for (int j = 0; j < currentSolution.size(); j++) {
                if (i != j) {
                    // Perform a local move by swapping items between bins
                    Bin binA = currentSolution.get(i);
                    Bin binB = currentSolution.get(j);
        
                    if (!binA.isEmpty() && !binB.isEmpty()) {
                        double itemA = binA.removeRandomItem();
                        double itemB = binB.removeRandomItem();
        
                        if (binA.add(itemB) && binB.add(itemA)) {
                            // Update fitness if the move improves the solution
                            double newFitness = calculateFitness(currentSolution);
                            if (newFitness < bestFitness) {
                                bestSolution = new ArrayList<>(currentSolution);
                                bestFitness = newFitness;
                                improved = true;
                            }
                        } else {
                            // Revert the move if it violates bin capacity constraints
                            binA.remove(itemB);
                            binB.remove(itemA);
                        }
                    }
                }
            }
        }

        numIterations++;
    } while (improved && numIterations < MAX_ITERATIONS);

        return bestSolution;
    }

    public void perturbation(List<Bin> bins, double perturbationRate) {
        Random random = new Random();
        int binIndex = random.nextInt(bins.size());
        Bin bin = bins.get(binIndex);
    
        if (!bin.isEmpty()) {
            int numItemsToPerturb = (int) Math.ceil(bin.size() * perturbationRate);
            List<Double> itemsToPerturb = new ArrayList<>();
    
            for (int i = 0; i < numItemsToPerturb; i++) {
                double removedItem = bin.removeRandomItem();
                itemsToPerturb.add(removedItem);
            }
    
            for (double item : itemsToPerturb) {
                binIndex = random.nextInt(bins.size());
    
                while (!bins.get(binIndex).add(item)) {
                    binIndex = random.nextInt(bins.size());
                }
            }
        }
    }
    

    public double calculateFitness(List<Bin> bins) {
        double totalFitness = 0.0;

        for (Bin bin : bins) {
            double binFitness = bin.getCurrentSize() / bin.getCapacity();
            totalFitness += Math.pow(binFitness, 2);
        }

        return totalFitness;
    }

    public void packBins() {
        List<Bin> currentSolution = generateInitialSolution();
        List<Bin> bestSolution = localSearch(currentSolution);
        double bestSolutionFitness = calculateFitness(bestSolution);

        // Output best solution
        System.out.println("Number of bins: " + bestSolution.size());
        System.out.println("Fitness: " + bestSolutionFitness);
    }

}
