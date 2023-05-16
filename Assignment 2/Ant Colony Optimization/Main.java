import java.io.File;
import java.util.ArrayList;

public class Main {
    
    public static void main(String[] args) {

        ArrayList<String> fileNames = readFromFile();

        for (String fileName : fileNames) {
            // Displaying the files name in colour to 
            String[] tokes = fileName.split("/");
            System.out.println("\u001B[32mFile Name: " + tokes[2] + "\u001B[0m"); 
        }
     
    }

    public static ArrayList<String> readFromFile() {    

        String directoryPath = "../Knapsack Instances";

        File directory = new File(directoryPath);

        File[] files = directory.listFiles();

        ArrayList<String> fileNames = new ArrayList<>();

        for (File file : files) {
            if (file.isFile() && !file.getName().endsWith(".xlsx")) {
                fileNames.add("../Knapsack Instances/" + file.getName());
            }
        }

        return fileNames;

    }
}