import java.util.Random;
import java.util.List;

public class Individual {
    private DecisionTree decisionTree;
    private double fitness;

    public Individual() {
        this.decisionTree = null;
        this.fitness = 0.0;
    }

    public Individual(DecisionTree decisionTree) {
        this.decisionTree = decisionTree;
        this.fitness = 0.0;
    }

    public DecisionTree getDecisionTree() {
        return decisionTree;
    }

    public void setDecisionTree(DecisionTree decisionTree) {
        this.decisionTree = decisionTree;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public void initializeRandomTree(int maxDepth, Random random) {
        decisionTree = new DecisionTree();
        decisionTree.initializeRandomTree(maxDepth, random);
    }

    // Evaluate the fitness of the individual by calculating the accuracy of the decision tree
    public void evaluateFitness(Dataset dataset) {
        int correctPredictions = 0;
        List<List<String>> data = dataset.getData();

        for (List<String> record : data) {
            String actualClass = record.get(0);
            String predictedClass = decisionTree.predict(record);
            if (predictedClass.equals(actualClass)) {
                correctPredictions++;
            }
        }

        fitness = (double) correctPredictions / data.size();
    }
}
