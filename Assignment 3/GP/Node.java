import java.util.ArrayList;
import java.util.List;

public class Node implements Cloneable {
    private String attribute;
    private String value;
    private List<Node> children;
    private String classLabel;
    private List<Branch> branches;

    public Node(String classLabel, String attribute, List<Branch> branches) {
        this.classLabel = classLabel;
        this.attribute = attribute;
        this.branches = branches;
    }

    public String getClassLabel() {
        return classLabel;
    }

    public String getAttribute() {
        return attribute;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public boolean isTerminalNode() {
        return classLabel != null;
    }

    public void addBranch(String attributeValue, Node subtree) {
        if (branches == null) {
            branches = new ArrayList<>();
        }
        branches.add(new Branch(attributeValue, subtree));
    }

    public Node getSubtreeWithAttributeValue(String attributeValue) {
        if (branches != null) {
            for (Branch branch : branches) {
                if (branch.getAttributeValue().equals(attributeValue)) {
                    return branch.getSubtree();
                }
            }
        }
        return null;
    }

    @Override
    protected Node clone() throws CloneNotSupportedException {
        Node clonedNode = (Node) super.clone();
        clonedNode.attribute = this.attribute;
        clonedNode.value = this.value;
        clonedNode.children = new ArrayList<>();
        for (Node child : this.children) {
            clonedNode.children.add(child.clone());
        }
        return clonedNode;
    }
}
