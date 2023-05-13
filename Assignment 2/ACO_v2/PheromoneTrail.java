import java.util.ArrayList;
import java.util.List;
public class PheromoneTrail {

    private List<Double> pheromoneTrail;

    public PheromoneTrail(int size) {
        pheromoneTrail = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            pheromoneTrail.add(1.0);
        }
    }

    public void update() {
        for (int i = 0; i < pheromoneTrail.size(); i++) {
            pheromoneTrail.set(i, (1 - AntColonyOptimization.RHO) * pheromoneTrail.get(i) + AntColonyOptimization.RHO * getPheromoneTrail(i, i));
        }
    }

    public double getPheromoneTrail(int fromIndex, int toIndex) {
        return pheromoneTrail.get(fromIndex);
    }

}