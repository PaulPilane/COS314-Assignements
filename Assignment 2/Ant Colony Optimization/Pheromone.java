public class Pheromone {
    private double level;
    
    public Pheromone() {
        level = 1.0;
    }
    
    public double getLevel() {
        return level;
    }
    
    public void update(double delta) {
        level *= (1 - delta);
        level += delta;
    }

    public void setLevel(double level) {
        this.level = level;
    }
}