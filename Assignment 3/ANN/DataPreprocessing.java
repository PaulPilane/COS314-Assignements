import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataPreprocessing {

    public static void main(String[] args) {
        String filePath = "../dataset/breast-cancer.data"; // Path to the dataset file
        List<List<String>> processedData = preprocessDataFromFile(filePath);

        // Print the processed data
        for (List<String> record : processedData) {
            System.out.println(record);
        }
    }

    public static List<List<String>> preprocessDataFromFile(String filePath) {
        List<List<String>> processedData = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line into its attributes
                String[] attributes = line.split(",");

                // Perform preprocessing on the attributes and create a processed record
                List<String> processedRecord = preprocessRecord(attributes);

                processedData.add(processedRecord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return processedData;
    }

    private static List<String> preprocessRecord(String[] attributes) {
        List<String> processedRecord = new ArrayList<>();

        // Preprocess attribute 1 (class label)
        String classLabel = processRecurrence(attributes[0]);
        processedRecord.add(classLabel);

        // Preprocess attribute 2 (age)
        String age = preprocessAge(attributes[1]);
        processedRecord.add(age);

        // Preprocess attribute 3 (menopause)
        String menopause = processMenopause(attributes[2]);
        processedRecord.add(menopause);

        // Preprocess attribute 4 (tumor-size)
        String tumorSize = preprocessTumorSize(attributes[3]);
        processedRecord.add(tumorSize);

        // Preprocess attribute 5 (inv-nodes)
        String invNodes = preprocessInvNodes(attributes[4]);
        processedRecord.add(invNodes);

        // Preprocess attribute 6 (node-caps)
        String nodeCaps = processYesNo(attributes[5]);
        processedRecord.add(nodeCaps);

        // Preprocess attribute 7 (deg-malig)
        String degMalig = preprocessDegMalig(attributes[6]);
        processedRecord.add(degMalig);

        // Preprocess attribute 8 (breast)
        String breast = processLeftRight(attributes[7]);
        processedRecord.add(breast);

        // Preprocess attribute 9 (breast-quad)
        String breastQuad = breastQuad(attributes[8]);
        processedRecord.add(breastQuad);

        // Preprocess attribute 10 (irradiat)
        String irradiat =  processYesNo(attributes[9]);
        processedRecord.add(irradiat);

        return processedRecord;
    }

    // Preprocessing methods for individual attributes

    private static String preprocessAge(String age) {
        // Perform preprocessing on age attribute
        // E.g., convert age range to a single value
        String[] ageRange = age.split("-");
        int lowerBound = Integer.parseInt(ageRange[0]);
        int upperBound = Integer.parseInt(ageRange[1]);
        int averageAge = (lowerBound + upperBound) / 2;
        return String.valueOf(averageAge);
    }

    private static String preprocessTumorSize(String tumorSize) {
        // Perform preprocessing on tumor-size attribute
        // E.g., convert tumor size range to a single value
        String[] sizeRange = tumorSize.split("-");
        int lowerBound = Integer.parseInt(sizeRange[0]);
        int upperBound = Integer.parseInt(sizeRange[1]);
        int averageSize = (lowerBound + upperBound) / 2;
        return String.valueOf(averageSize);
    }

    private static String preprocessInvNodes(String invNodes) {
        // Perform preprocessing on inv-nodes attribute
        // E.g., convert inv-nodes range to a single value
        String[] nodesRange = invNodes.split("-");
        int lowerBound = Integer.parseInt(nodesRange[0]);
        int upperBound = Integer.parseInt(nodesRange[1]);
        int averageNodes = (lowerBound + upperBound) / 2;
        return String.valueOf(averageNodes);
    }

    private static String preprocessDegMalig(String degMalig) {
        // Perform preprocessing on deg-malig attribute
        // E.g., convert deg-malig to a categorical value
        switch (degMalig) {
            case "1":
                return "1";
            case "2":
                return "2";
            case "3":
                return "3";
            default:
                return "";
        }
    }

    public static String processRecurrence(String recurrence) {

        switch (recurrence) {
            case "no-recurrence-events":
                return "0";
            case "recurrence-events":
                return "1";
            default:
                return "";
        }
    }


    public static String processMenopause(String menopause){
        switch (menopause) {
            case "premeno":
                return "0";
            case "ge40":
                return "1";
            case "lt40":
                return "2";
            default:
                return "";
        }
    }

    public static String processYesNo(String answer){
        switch (answer) {
            case "yes":
                return "1";
            case "no":
                return "0";
            default:
                return "";
        }   
    }

    public static String processLeftRight(String side) {
        switch (side) {
            case "left":
                return "0";
            case "right":
                return "1";
            default:
                return "";
        }
    }

    public static String breastQuad(String quad) {
        switch (quad) {
            case "left_up":
                return "0";
            case "left_low":
                return "1";
            case "right_up":
                return "2";
            case "right_low":
                return "3";
            case "central":
                return "4";
            default:
                return "";
        }
    }
}
