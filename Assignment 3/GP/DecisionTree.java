import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DecisionTree {
    private Node root;

    public DecisionTree() {
        this.root = null;
    }

    public void initializeRandomTree(int maxDepth, Random random) {
        int currentDepth = 0;
        root = createRandomSubtree(currentDepth, maxDepth, random);
    }

    private Node createRandomSubtree(int currentDepth, int maxDepth, Random random) {
        if (currentDepth >= maxDepth || random.nextDouble() < 0.5) {
            // Create a terminal node
            String randomClass = getRandomClass();
            return new Node(randomClass, null, null);
        } else {
            // Create a non-terminal node
            String randomAttribute = getRandomAttribute();
            List<Branch> branches = new ArrayList<>();

            for (String attributeValue : getAllAttributeValues(randomAttribute)) {
                Node subtree = createRandomSubtree(currentDepth + 1, maxDepth, random);
                branches.add(new Branch(attributeValue, subtree));
            }

            return new Node(null, randomAttribute, branches);
        }
    }

    public String predict(List<String> record) {
        return traverseTree(root, record);
    }

    private String traverseTree(Node node, List<String> record) {
        if (node.isTerminalNode()) {
            return node.getClassLabel();
        } else {
            String attributeValue = record.get(getAttributeIndex(node.getAttribute()));
            for (Branch branch : node.getBranches()) {
                if (branch.getAttributeValue().equals(attributeValue)) {
                    return traverseTree(branch.getSubtree(), record);
                }
            }
            return null; // Attribute value not found, handle accordingly
        }
    }

    public DecisionTree crossover(DecisionTree partnerTree, Random random) throws CloneNotSupportedException {
        DecisionTree offspring = new DecisionTree();
        Node newRoot = crossoverSubtrees(root, partnerTree.getRoot(), random);
        offspring.setRoot(newRoot);
        return offspring;
    }

    private Node crossoverSubtrees(Node node1, Node node2, Random random) throws CloneNotSupportedException {
        if (random.nextDouble() < 0.5) {
            return node1.clone();
        } else {
            Node newNode = node2.clone();

            if (!newNode.isTerminalNode() && !node1.isTerminalNode()) {
                int numBranches1 = node1.getBranches().size();
                int numBranches2 = node2.getBranches().size();
                int minBranches = Math.min(numBranches1, numBranches2);

                // Randomly select the number of branches to inherit from each parent
                int numBranchesInherited = random.nextInt(minBranches + 1);

                // Inherit branches from node1
                for (int i = 0; i < numBranchesInherited; i++) {
                    Branch branch = node1.getBranches().get(i);
                    newNode.addBranch(branch.getAttributeValue(), crossoverSubtrees(branch.getSubtree(),
                            newNode.getSubtreeWithAttributeValue(branch.getAttributeValue()), random));
                }

                // Inherit remaining branches from node2
                for (int i = numBranchesInherited; i < numBranches2; i++) {
                    Branch branch = node2.getBranches().get(i);
                    newNode.addBranch(branch.getAttributeValue(), branch.getSubtree());
                }
            }

            return newNode;
        }
    }

    public void mutate(Random random) {
        mutateSubtree(root, random);
    }

    private void mutateSubtree(Node node, Random random) {
        if (!node.isTerminalNode()) {
            if (random.nextDouble() < 0.1) { // Mutation probability
                int numBranches = node.getBranches().size();
                if (numBranches > 0) {
                    int randomBranchIndex = random.nextInt(numBranches);
                    Branch randomBranch = node.getBranches().get(randomBranchIndex);
                    randomBranch.setSubtree(createRandomSubtree(0, 2, random));
                }
            } else {
                for (Branch branch : node.getBranches()) {
                    mutateSubtree(branch.getSubtree(), random);
                }
            }
        }
    }

    private String getRandomClass() {
        // Return the available classes or modify as per your dataset
        String[] classes = {"no-recurrence-events", "recurrence-events"};
        Random random = new Random();
        return classes[random.nextInt(classes.length)];
    }

    private String getRandomAttribute() {
        // Return the available attributes or modify as per your dataset
        String[] attributes = {"age", "menopause", "tumor-size", "inv-nodes", "node-caps",
                "deg-malig", "breast", "breast-quad", "irradiat"};
        Random random = new Random();
        return attributes[random.nextInt(attributes.length)];
    }

    private List<String> getAllAttributeValues(String attribute) {
        // Return the available attribute values or modify as per your dataset
        List<String> attributeValues = new ArrayList<>();
        if (attribute.equals("age")) {
            attributeValues.add("10-19");
            attributeValues.add("20-29");
            attributeValues.add("30-39");
            attributeValues.add("40-49");
            attributeValues.add("50-59");
            attributeValues.add("60-69");
            attributeValues.add("70-79");
            attributeValues.add("80-89");
            attributeValues.add("90-99");
        } else if (attribute.equals("menopause")) {
            attributeValues.add("lt40");
            attributeValues.add("ge40");
            attributeValues.add("premeno");
        } else if (attribute.equals("tumor-size")) {
            attributeValues.add("0-4");
            attributeValues.add("5-9");
            attributeValues.add("10-14");
            attributeValues.add("15-19");
            attributeValues.add("20-24");
            attributeValues.add("25-29");
            attributeValues.add("30-34");
            attributeValues.add("35-39");
            attributeValues.add("40-44");
            attributeValues.add("45-49");
            attributeValues.add("50-54");
            attributeValues.add("55-59");
        } else if (attribute.equals("inv-nodes")) {
            attributeValues.add("0-2");
            attributeValues.add("3-5");
            attributeValues.add("6-8");
            attributeValues.add("9-11");
            attributeValues.add("12-14");
            attributeValues.add("15-17");
            attributeValues.add("18-20");
            attributeValues.add("21-23");
            attributeValues.add("24-26");
            attributeValues.add("27-29");
            attributeValues.add("30-32");
            attributeValues.add("33-35");
            attributeValues.add("36-39");
        } else if (attribute.equals("node-caps")) {
            attributeValues.add("yes");
            attributeValues.add("no");
            attributeValues.add("?"); // For missing values
        } else if (attribute.equals("deg-malig")) {
            attributeValues.add("1");
            attributeValues.add("2");
            attributeValues.add("3");
        } else if (attribute.equals("breast")) {
            attributeValues.add("left");
            attributeValues.add("right");
        } else if (attribute.equals("breast-quad")) {
            attributeValues.add("left-up");
            attributeValues.add("left-low");
            attributeValues.add("right-up");
            attributeValues.add("right-low");
            attributeValues.add("central");
            attributeValues.add("?"); // For missing values
        } else if (attribute.equals("irradiat")) {
            attributeValues.add("yes");
            attributeValues.add("no");
        }
        return attributeValues;
    }

    private int getAttributeIndex(String attribute) {
        // Return the index of the attribute or modify as per your dataset
        switch (attribute) {
            case "age":
                return 1;
            case "menopause":
                return 2;
            case "tumor-size":
                return 3;
            case "inv-nodes":
                return 4;
            case "node-caps":
                return 5;
            case "deg-malig":
                return 6;
            case "breast":
                return 7;
            case "breast-quad":
                return 8;
            case "irradiat":
                return 9;
            default:
                return -1; // Attribute not found
        }
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }
}
