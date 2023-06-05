import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        Dataset[] set = readData();
        GeneticProgramming gp = new GeneticProgramming(set);
        gp.runAlgorithm();
        System.out.println();
        System.out.print("Please enter fill name to test: ");
        // Scanner sc = new Scanner(System.in);
        String name = "test.data";
        Dataset[] test = readDataTest(name);
        System.out.println("Prediction starting");
        gp.predict(test);
    }

    private static Dataset[] readDataTest(String name) {
        Dataset[] set = null;
        ArrayList<Dataset> instances = new ArrayList<>();

        // Open file breast-cancer.data
        File file = new File("../dataset/breast-cancer.data");
        int count = 286;
        try {
            
            Scanner myReader = new Scanner(file);

            set = new Dataset[count];
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataSplit = data.split(",");
                Dataset instance = new Dataset(dataSplit[0], dataSplit[1], dataSplit[2], dataSplit[3], dataSplit[4],
                        dataSplit[5], dataSplit[6], dataSplit[7], dataSplit[8], dataSplit[9]);
                instances.add(instance);
            }
            myReader.close();
        } catch (Exception e) {
            // Print the stack trace
        }

        return instances.toArray(new Dataset[instances.size()]);
    }

    private static Dataset[] readData() {
        Dataset[] set = null;
        ArrayList<Dataset> instances = new ArrayList<>();

        // Open file breast-cancer.data
        File file = new File("../dataset/breast-cancer.data");
        int count = 286;
        try {
            
            Scanner myReader = new Scanner(file);

            set = new Dataset[count];
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String[] dataSplit = data.split(",");
                Dataset instance = new Dataset(dataSplit[0], dataSplit[1], dataSplit[2], dataSplit[3], dataSplit[4],
                        dataSplit[5], dataSplit[6], dataSplit[7], dataSplit[8], dataSplit[9]);
                instances.add(instance);
            }
            myReader.close();
        } catch (Exception e) {
            // Print the stack trace
        }

        return instances.toArray(new Dataset[instances.size()]);
    }
}