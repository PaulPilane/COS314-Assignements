import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;

public class Main {
    
    public static void main(String[] args) {
        // TODO code application logic here


        ArrayList<String> fileNames = readData();
        for (String file : fileNames) {
            String[] tokens = file.split("/");
            System.out.println("\n\u001B[32mFile Name: " + tokens[2] + "\u001B[0m\n");
            displayContent(file);
        }
        
    }

    public static ArrayList<String> readData() {

        String directoryPath = "../dataset";

        File directory = new File(directoryPath);

        File[] files = directory.listFiles();

        ArrayList<String> fileNames = new ArrayList<>();

        for (File file : files) {
            if (file.isFile() && !file.getName().endsWith(".xlsx")) {
                fileNames.add("../dataset/" + file.getName());
            }
        }

        return fileNames;

    }


    public static List<String> displayContent(String fileName) {
        List<String> instances = new ArrayList<>();
        try {
            Scanner scanner = new Scanner(new File(fileName));
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                instances.add(line);
                System.out.println(line);
            }

            scanner.close();
            
        } catch (IOException e) {
            System.err.println("Error reading input file: " + e.getMessage());

            // Shut down the JVM when there is file read error 
            System.exit(1);
        }


        return  instances;

    }

    public static List<String> preProcess(){


        	
        return null;
    }
}
