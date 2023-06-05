import java.util.List;


public class Main {
    public static void main(String[] args) {
        System.out.println("Here we are testing!");
        Dataset dataset = new Dataset();
        List<List<String>> data = dataset.getData();

        GPAlgorithm gaAlgorithm = new GPAlgorithm(100, 50, 0.1, 0.8);
        DecisionTree bestDecisionTree = gaAlgorithm.evolveDecisionTree(data);
        System.out.println("Best Decision Tree: " + bestDecisionTree);
    }
}
