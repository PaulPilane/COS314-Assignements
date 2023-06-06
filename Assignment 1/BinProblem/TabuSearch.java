import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TabuSearch {
    private List<Double> items;
    private List<Bin> bins;
    private int TABUSIZE = 100;
    private Double capacity;

    public TabuSearch(List<Double> items, Double capacity) {
        this.items = items;
        this.bins = new ArrayList<>();
        this.capacity = capacity;
    }

    public List<Bin> packBins() {
        // Initialize bins
        bins.clear();
        bins.add(new Bin(capacity));

        // Sort items in descending order
        items.sort((a, b) -> Double.compare(b, a));

        // Greedy initial packing
        for (Double item : items) {
            boolean packed = false;
            for (Bin bin : bins) {
                if (bin.add(item)) {
                    packed = true;
                    break;
                }
            }
            if (!packed) {
                Bin newBin = new Bin(capacity);
                newBin.add(item);
                bins.add(newBin);
            }
        }

        // Perform Tabu search refinement
        int tabuCount = 0;
        List<Double> tabuList = new ArrayList<>();
        double bestScore = calculateScore();
        List<Bin> bestSolution = new ArrayList<>(bins);

        while (tabuCount < TABUSIZE) {

            List<Double> neighborMoves = generateNeighborMoves();
            double bestNeighborScore = Double.MAX_VALUE;
            List<Bin> bestNeighborSolution = null;
            double bestMove = 0;

            for (Double move : neighborMoves) {
                performMove(move);

                double neighborScore = calculateScore();
                if (neighborScore < bestNeighborScore) {
                    bestNeighborScore = neighborScore;
                    bestNeighborSolution = new ArrayList<>(bins);
                    bestMove = move;
                }

                undoMove(move);
            }

            performMove(bestMove);
            tabuList.add(bestMove);
            tabuCount++;

            if (bestNeighborScore < bestScore) {
                bestScore = bestNeighborScore;
                bestSolution = bestNeighborSolution;
                tabuCount = 0;
            }

            if (tabuList.size() > TABUSIZE) {
                tabuList.remove(0);
            }
        }

        System.out.println("Number of bins: " + bestSolution.size() + "\n");
        return bestSolution;
    }

    private List<Double> generateNeighborMoves() {
        List<Double> moves = new ArrayList<>();

        for (Bin bin : bins) {
            if (bin.size() > 1) {
                for (Double item : bin.getItems()) {
                    moves.add(item);
                }
            }
        }

        return moves;
    }

    private void performMove(double item) {
        for (Bin bin : bins) {
            if (bin.remove(item)) {
                break;
            }
        }

        Random random = new Random();
        int binIndex = random.nextInt(bins.size());
        bins.get(binIndex).add(item);
    }

    private void undoMove(double item) {
        for (Bin bin : bins) {
            if (bin.remove(item)) {
                break;
            }
        }

        bins.get(0).add(item);
    }

    private double calculateScore() {
        double score = 0;
        for (Bin bin : bins) {
            double binScore = Math.pow(bin.getCurrentSize() - 500, 2);
            score += binScore;
        }
        return score;
    }
}
