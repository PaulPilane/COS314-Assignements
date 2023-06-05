public class Branch {
    private String attributeValue;
    private Node subtree;

    public Branch(String attributeValue, Node subtree) {
        this.attributeValue = attributeValue;
        this.subtree = subtree;
    }

    public String getAttributeValue() {
        return attributeValue;
    }

    public Node getSubtree() {
        return subtree;
    }

    public void setSubtree(Node subtree) {
        this.subtree = subtree;
    }

    public Branch clone() throws CloneNotSupportedException {
        Node clonedSubtree = subtree != null ? subtree.clone() : null;
        return new Branch(attributeValue, clonedSubtree);
    }
}
