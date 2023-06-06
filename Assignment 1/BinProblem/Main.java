import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        run();
    }

    public static void run() {

    File folders = new File("../instances");

    File[] files = folders.listFiles();

    for (File file : files) {
        if (file.exists()) {
            String pathName = file.getPath().split("/")[2];

            if(pathName.equals("Falkenauer")) {
                File falkenauerFilePath = new File(file.getPath() + "/Falkenauer_T");
                File []falkenauerFiles = falkenauerFilePath.listFiles();
                processFiles(falkenauerFiles);

                falkenauerFilePath = new File(file.getPath() + "/Falkenauer_U");
                falkenauerFiles = falkenauerFilePath.listFiles();
                // processFiles(falkenauerFiles);
            }

            if(pathName.equals("Hard28")) {
                File hard28FilePath = new File(file.getPath());
                File[] hard28Files = hard28FilePath.listFiles();
                // processFiles(hard28Files);
            }

            if(pathName.equals("Scholl")) {
                File schollFilePath = new File(file.getPath() + "/Scholl_1");
                File []schollFiles = schollFilePath.listFiles();
                // processFiles(schollFiles);

                schollFilePath = new File(file.getPath() + "/Scholl_2");
                schollFiles = schollFilePath.listFiles();
                // processFiles(schollFiles);

                schollFilePath = new File(file.getPath() + "/Scholl_3");
                schollFiles = schollFilePath.listFiles();
                // processFiles(schollFiles);
            }

            if(pathName.equals("Schwerin")) {
                File schwerinFilePath = new File(file.getPath() + "/Schwerin_1");
                File []schwerinFiles = schwerinFilePath.listFiles();
                // processFiles(schwerinFiles);
                
                schwerinFilePath = new File(file.getPath() + "/Schwerin_2");
                schwerinFiles = schwerinFilePath.listFiles();
                // processFiles(schwerinFiles);

            }

            if(pathName.equals("Waescher")) {
                File waescherFilePath = new File(file.getPath());
                File []waescherFiles = waescherFilePath.listFiles();
                // processFiles(waescherFiles);
            }

        }
    }
    }


    public static void processFiles(File[] files) {
        ILS local;
        TabuSearch tabu;
        List<Double> items = new ArrayList<>();
        double duration = 0;
        
        System.out.println("\u001B[32mITERATIVE LOCAL SEARCH\u001B[0m");
        for (File file : files) {
            System.out.println("File Name: " + file.getName());
            double start = System.currentTimeMillis();
            try (Scanner scanner = new Scanner(file)) {
                // System.out.println("File Name: " + file.getName());
                scanner.nextInt(); // This is for the size;
                double capacity = scanner.nextInt();

                while (scanner.hasNext()) {

                    double item = scanner.nextInt();
                    items.add(item);
                }

                // Iterative Local Search
                
                local = new ILS(items, capacity);
                local.packBins();
                


                scanner.close();
            } catch (FileNotFoundException e) {
        
                e.printStackTrace();
            }

            double end = System.currentTimeMillis();
            duration += ((end - start)/1000);
            items.clear();
            
        }

        duration /= files.length;

        System.out.println("Average Time: " + duration + "s");

            
            // Tabu search
        System.out.println("\n\u001B[36mTABU SEARCH\u001B[0m");

        duration = 0;
        for (File file : files) {
            double start = (double) System.currentTimeMillis();
            try (Scanner scanner = new Scanner(file)) {
                System.out.println("File Name: " + file.getName());
                
                scanner.nextInt(); // This is for the size;
                double capacity = scanner.nextInt();

                while (scanner.hasNext()) {

                    double item = scanner.nextInt();
                    items.add(item);
                }

                tabu = new TabuSearch(items, capacity);
                tabu.packBins();

                scanner.close();
            } catch (FileNotFoundException e) {
        
                e.printStackTrace();
            }

            double end = (double) System.currentTimeMillis();
            duration += ((end - start)/1000);

            items.clear();
            // break;
            
        }

        duration /= files.length;

        System.out.println("Average Time: " + duration + "s");
    }
}