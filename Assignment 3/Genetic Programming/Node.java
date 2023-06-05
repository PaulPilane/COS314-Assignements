import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Node {
    private String value;
    private List<Node> children = new ArrayList<>();
    private List<String> edges = new ArrayList<>();
    private Boolean isLeaf = false;
    private double fitness = 0.0;

    public Node(String value) {
        this.value = value;
    }

    public Node(String value, Boolean isLeaf) {
        this.value = value;
        this.isLeaf = isLeaf;
    }

    public String getValue() {
        return this.value;
    }

    public List<Node> getChildren() {
        return this.children;
    }

    public Boolean isLeaf() {
        return this.isLeaf;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public void setIsLeaf(Boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    // Add a child
    public void addChild(Node child) {
        this.children.add(child);
    }

    // Add an edge
    public void addEdge(String edge) {
        this.edges.add(edge);
    }

    public boolean evaluate(Map<String, String> attr) {
        if (isLeaf == true) {
            boolean ans = value.equals(attr.get("Class"));
            return ans;
        }
        for (int i = 0; i < edges.size(); i++) {
            if (attr.get(value).equals(edges.get(i))) {
                return children.get(i).evaluate(attr);
            }
        }
        return false;
    }

    public Node findChild(String attribute) {
        if (value.equals(attribute)) {
            return null;
        }
        // Loop over children
        for (int i = 0; i < edges.size(); i++) {
            Node child = children.get(i);
            if (child.getValue().equals(attribute)) {
                return this;
            } else {
                Node temp = child.findChild(attribute);
                if (temp != null) {
                    return temp;
                }
            }
        }
        return null;
    }

    public Node swap(Node swap2, String attribute) {
        Node temp = copy();
        // Loop over children
        for (int i = 0; i < edges.size(); i++) {
            Node child = children.get(i);
            if (child.getValue().equals(attribute)) {

                temp.getChildren().set(i, swap2.copy());
                // children.set(i, swap2);
                return temp;
            }
        }

        return null;
    }

    public Node copy() {
        if (isLeaf == true) {
            return new Node(value, isLeaf);
        }
        Node copy = new Node(value, isLeaf);
        for (int i = 0; i < edges.size(); i++) {
            copy.addEdge(edges.get(i));
            copy.addChild(children.get(i).copy());
        }
        return copy;
    }

    public Node getRandomLeaf() {
        if (isLeaf == true) {
            return this;
        }
        // Generate a random number in the children range
        int index = (int) (Math.random() * children.size());
        return children.get(index).getRandomLeaf();
    }

    public Node getNodeAtLevel(int level, int i) {

        if (level == i || isLeaf == true) {
            return this;
        }
        for (int j = 0; j < children.size(); j++) {
            Node child = children.get(j);
            Node temp = child.getNodeAtLevel(level, i + 1);
            if (temp != null) {
                return temp;
            }
        }
        
        return null;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return this.fitness;
    }

    public Node getRandomNode() {
        // Generate a random number in the children range
        int index = (int) (Math.random() * children.size());
        return children.get(index);
    }

    public void makeLeaf(String value) {
        this.value = value;
        this.isLeaf = true;
        this.children = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public String toString() {
        String ans = "";
        if (isLeaf == true) {
            ans += value;
        } else {
            ans += value + " ";
            for (int i = 0; i < edges.size(); i++) {
                ans += edges.get(i) + " ";
            }
            for (int i = 0; i < children.size(); i++) {
                ans += children.get(i).toString();
            }
        }
        return ans;
    }
}
