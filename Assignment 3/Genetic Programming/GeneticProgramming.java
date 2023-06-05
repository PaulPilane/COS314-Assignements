import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GeneticProgramming {
    private int populationSize = 100;
    private int maxGeneration = 50;
    private double mutationRate = 0.05;
    private double crossoverRate = 0.7;
    private int maxTreeDepth = 4;
    private Node bestTree = null;
    double bestFitness = 0;
    Dataset[] dataset;
    List<Node> population = new ArrayList<Node>();
    List<Double> fitness = new ArrayList<Double>();
    Map<String, String[]> attributes = new HashMap<String, String[]>();

    public GeneticProgramming(Dataset[] dataset) {
        this.dataset = dataset;
        attributes.put("age", new String[] { "10-19", "20-29", "30-39", "40-49", "50-59", "60-69",
                "70-79", "80-89", "90-99" });
        attributes.put("menopause", new String[] { "premeno", "ge40", "lt40" });
        attributes.put("tumor-size", new String[] { "0-4", "5-9", "10-14", "15-19", "20-24", "25-29", "30-34",
                "35-39", "40-44", "45-49", "50-54", "55-59" });
        attributes.put("inv-nodes", new String[] { "0-2", "3-5", "6-8", "9-11", "12-14", "15-17", "18-20",
                "21-23", "24-26", "27-29", "30-32", "33-35", "36-39" });
        attributes.put("node-caps", new String[] { "yes", "no" });
        attributes.put("deg-malig", new String[] { "1", "2", "3" });
        attributes.put("breast", new String[] { "left", "right" });
        attributes.put("breast-quad", new String[] { "left-up", "left-low", "right-up", "right-low", "central" });
        attributes.put("irradiat", new String[] { "yes", "no" });

    }

    public void createInitialPopulation() {
        for (int i = 0; i < populationSize; i++) {
            // Store a temp of attributes
            Map<String, String[]> attr = new HashMap<String, String[]>(attributes);
            // Generate a random number between 0 and 9
            Node root = createTree(attr, 0);
            if (attr.size() != 0) {

                System.out.println("Error: " + attr.size());
            }
            population.add(root);
        }
    }

    private Node createTree(Map<String, String[]> attr, int i) {
        // Generate a random number between 0 and 9
        if (attr.size() == 0) {
            return null;
        }
        Random rand = new Random();
        int index = rand.nextInt(attr.size());
        String attribute = (String) attr.keySet().toArray()[index];
        String[] edges = attr.get(attribute);
        attr.remove(attribute);
        Node node = new Node(attribute);
        if (i + 1 < maxTreeDepth) {
            for (String edge : edges) {
                Node child = createTree(attr, i + 1);
                if (child != null) {
                    node.addChild(child);
                    node.addEdge(edge);
                } else {
                    Random random = new Random();
                    int choose = random.nextBoolean() ? 1 : 0;
                    String value = choose == 1 ? "recurrence-events" : "no-recurrence-events";
                    node.addChild(new Node(value, true));
                    node.addEdge(edge);
                }

            }
        } else {
            // Choose random number between 0 and 1
            for (String edge : edges) {
                Random random = new Random();
                int choose = random.nextBoolean() ? 1 : 0;
                String value = choose == 1 ? "recurrence-events" : "no-recurrence-events";
                node.addChild(new Node(value, true));
                node.addEdge(edge);
            }
        }
        return node;
    }

    public void runAlgorithm() {
        createInitialPopulation();
        for (int i = 0; i < maxGeneration; i++) {
            // Evaluate
            evaluate(population, fitness);
            averageAccuracy(i + 1);
            Node best = getBestSolution();
            if (bestTree == null) {
                bestTree = best;
                bestFitness = evaluate(best);
            } else if (evaluate(best) > bestFitness) {
                bestTree = best;
                bestFitness = evaluate(best);
            }
           
            List<Node> nextGen = new ArrayList<Node>();
            while (nextGen.size() < populationSize) {
                // selectParents
                List<Node> parents = selectParents();
                evaluate(parents.get(0));
                evaluate(parents.get(1));
                // Crossover
                List<Node> children = crossover(parents);
                // Mutate
                mutate(children);
                // add children in nextGen
                // Loop over children
                nextGen.addAll(children);
            }
            population = nextGen;

        }
        evaluate(population, fitness);
        averageAccuracy(maxGeneration);
        Node best = getBestSolution();
        if (bestTree == null) {
            bestTree = best;
            bestFitness = evaluate(best);
        } else if (evaluate(best) > bestFitness) {
            bestTree = best;
            bestFitness = evaluate(best);
        }
        double fitness = evaluate(bestTree);
        System.out.println();
        System.out.println("Fitness: " + fitness);
        System.out.println("Best Accuracy: " + fitness / dataset.length * 100 + "%");
        fMeasure(best);

    }

    private void averageAccuracy(int i) {
        double sum = 0.0;
        for (Double fit : fitness) {
            sum += fit;
        }

        System.out.println("Generation " + i + " Average Accuracy: " + sum / fitness.size() / dataset.length * 100 + "%");
    }

    private Node getBestSolution() {
        double fit = 0.0;
        int index = 0;
        for (int i = 0; i < fitness.size(); i++) {
            if (fitness.get(i) > fit) {
                fit = fitness.get(i);
                index = i;
            }
        }
        return population.get(index);
    }

    private void mutate(List<Node> children) {
        Random rand = new Random();
        for (Node child : children) {
            int level = rand.nextInt(maxTreeDepth - 1) + 1;
            Node node = child.getNodeAtLevel(level, 0);
            if (node.isLeaf()) {
                Random random = new Random();
                int choose = random.nextBoolean() ? 1 : 0;
                String value = choose == 1 ? "recurrence-events" : "no-recurrence-events";
                node.setValue(value);
            } else {
                Random random = new Random();
                int choose = random.nextBoolean() ? 1 : 0;
                String value = choose == 1 ? "recurrence-events" : "no-recurrence-events";
                node.makeLeaf(value);
            }
        }
    } 

    private List<Node> crossover(List<Node> parents) {
        List<Node> children = new ArrayList<>();
        // Pick a random level to swap sub trees that is greater than 0
        Random rand = new Random();
        int level = rand.nextInt(maxTreeDepth - 1) + 1;

        Node parent1 = parents.get(0);
        Node parent2 = parents.get(1);
        Node child1 = parent1.copy();
        Node child2 = parent2.copy();

        child1 = child1.getNodeAtLevel(level, 0);
        child2 = child2.getNodeAtLevel(level, 0);
        // Swap subtrees
        swapSubTrees(child1, child2, level);
        children.add(child1);
        children.add(child2);

        return children;

    }

    private void swapSubTrees(Node child1, Node child2, int level) {
        if (child1.isLeaf() || child2.isLeaf()) {
            return;
        }
        if (level == 0) {
            Node temp = child1;
            child1 = child2;
            child2 = temp;
        } else {

            Random rand = new Random();
            int index1 = rand.nextInt(child1.getChildren().size());
            int index2 = rand.nextInt(child2.getChildren().size());
            Node child1Node = child1.getChildren().get(index1);
            Node child2Node = child2.getChildren().get(index2);
            child1.getChildren().set(index1, child2Node);
            child2.getChildren().set(index2, child1Node);
            swapSubTrees(child1Node, child2Node, level - 1);
        }
    }

    // Selection Type = tournament selection
    private List<Node> selectParents() {
        
        List<Node> newPopulation = new ArrayList<>();
        
        Random rand = new Random();
        for (int i = 0; i < 30; i++) {
            int index = rand.nextInt(population.size());
            Node node = population.get(index);
            newPopulation.add(node);
        }
        double first = 0.0, second = 0.0;
        Node node1 = null, node2 = null;
        for (int i = 0; i < newPopulation.size(); i++) {
            double fitness = evaluate(newPopulation.get(i));
            if (fitness > first) {
                second = first;
                first = fitness;
                node2 = node1;
                node1 = newPopulation.get(i);
            } else if (fitness > second) {
                second = fitness;
                node2 = newPopulation.get(i);
            }
        }

        List<Node> selectedParents = new ArrayList<Node>();
        selectedParents.add(node1);
        selectedParents.add(node2);
        return selectedParents;
    }

    // Fitness function 
    private void evaluate(List<Node> pop, List<Double> fit) {
        fit.clear();
        for (Node node : pop) {
            double fitness = 0;
            for (Dataset data : dataset) {
                if (node.evaluate(data.attr) == true) {
                    ++fitness;
                }
            }
            node.setFitness(fitness);
            // System.out.println(fitness);
            fit.add(fitness);
        }
    }

    // fitness function 
    private double evaluate(Node node) {

        double fitness = 0;
        for (int i = 0; i < dataset.length; i++) {

            Dataset data = dataset[i];
            Boolean ans = node.evaluate(data.attr);
            if (ans == true) {
                fitness += 1;
            }
        }

        node.setFitness(fitness);
        return fitness;
    }

    //f-measure
    private void fMeasure(Node node) {
        // Postive class = no-recurrence-events - 1
        // Negative class = recurrence-events - 0
        int tp = 0, tn = 0, fp = 0, fn = 0;
        for (int i = 0; i < dataset.length; i++) {

            Dataset data = dataset[i];
            Boolean ans = node.evaluate(data.attr);
            // Correctly predicted positive class
            if (data.getOutput() == 1) {
                if (ans == true)
                    tp++;
                else
                    fp++;
            } else {
                if (ans == true)
                    tn++;
                else
                    fn++;
            }
        }

        double precision = (double) tp / (tp + fp);
        double recall = (double) tp / (tp + fn);
        double fMeasure = (double) 2 * precision * recall / (precision + recall);
        System.out.println("Precision: " + precision);
        System.out.println("Recall: " + recall);
        System.out.println("F-Measure: " + fMeasure);

    }

    public String predict(Dataset[] test) {
        int tp = 0, tn = 0, fp = 0, fn = 0;
        int correct = 0;
        for (int i = 0; i < test.length; i++) {
            Boolean ans = bestTree.evaluate(test[i].attr);
            String Class = test[i].getOutput() == 0 ? "recurrence-events" : "no-recurrence-events";
            if (ans == true) {
                // System.out.println("Instance " + (i + 1) + ":\nPredicted: " + Class + "\nActual: " + Class + "\n");
                correct++;
            } else {
                String op = Class.equals("recurrence-events") ? "no-recurrence-events" : "recurrence-events";
                // System.out.println("Instance " + (i + 1) + ":\nPredicted: " + op + "\nActual: " + Class + "\n");
            }

        }
        System.out.println("Correct: " + correct + " Total: " + test.length);
        System.out.println("Accuracy: " + (double) correct / test.length * 100 + "%");
        return null;
    }

}
