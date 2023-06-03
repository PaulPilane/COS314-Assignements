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
    public int evaluate(int[] solution) {
        int totalValue = 0;
        int totalWeight = 0;
    
        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == 1) {
                totalValue += items[i].getValue();
                totalWeight += items[i].getWeight();
            }
        }
    
        if (totalWeight > capacity) {
            return -1;
        } else {
            return totalValue;
        }
    }

    public double getTotalWeight(int[] solution) {
        int totalWeight = 0;

        for (int i = 0; i < solution.length; i++) {
            if (solution[i] == 1) {
                totalWeight += items[i].getWeight();
            }
        }

        return totalWeight;
    }
}