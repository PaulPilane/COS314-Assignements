public class KnapsackProblem {
    private Item [] items;
    
    private double capacity;

    public KnapsackProblem(Item []items, double capacity) {
        this.items = items;
        this.capacity = capacity;
    }

    public double getValue(int item) {
        return items[item].getValue();
    }
    
    public double getWeight(int item) {
        return items[item].getWeight();
    }

    public double getCapacity() {
        return capacity;
    }

    public int getNumItems() {
        return items.length;
    }
}