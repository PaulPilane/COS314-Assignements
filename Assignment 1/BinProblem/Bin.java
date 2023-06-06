import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Bin {

    private double capacity;
    private List<Double> items;

    public Bin(double capacity) {
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public boolean add(double item) {
        if (getCurrentSize() + item <= capacity) {
            this.items.add(item);
            return true;
        }
        return false;
    }

    public boolean remove(double item) {

        if (this.items.contains(item)) {
            this.items.remove(item);
            return true;
        }
        return false;
    }

    public double getCapacity() {
        return capacity;
    }

    public double getCurrentSize() {
        int count = 0;

        for (Double item : items) {
            count += item;
        }

        return count;
    }

    public double getRemainingSize() {
        return capacity - getCurrentSize();
    }

    public boolean isFull() {
        return getCurrentSize() == capacity;
    }

    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    public void empty() {
        this.items.clear();;
    }

    public Integer size() {
       return this.items.size();
    }

    public double removeRandomItem() {

        Random random = new Random();
        int index = random.nextInt(this.items.size());
        double item = this.items.get(index);
        remove(item);

        return item;
    }

    public List<Double> getItems() {
        return this.items;
    }

    @Override
    public String toString() {
        return "Bin{" +
                "capacity=" + capacity +
                ", currentSize=" + this.getCurrentSize() +
                '}';
    }
}