import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Dataset {
    List<List<String>> processedData;

    public Dataset() {
        String filePath = "../dataset/breast-cancer.data"; // Path to the dataset file
        this.processedData = preprocessDataFromFile(filePath);

        // Print the processed data
        // for (List<String> record : processedData) {
        //     System.out.println(record);
        // }
    }

    public List<List<String>> getData() {
        return processedData;
    }


    private List<List<String>> preprocessDataFromFile(String filePath) {
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

    private List<String> preprocessRecord(String[] attributes) {
        List<String> processedRecord = new ArrayList<>();

        // Preprocess attribute 1 (class label)
        processedRecord.add(attributes[0]);

        // Preprocess attribute 2 (age)
        processedRecord.add(attributes[1]);

        // Preprocess attribute 3 (menopause)
        processedRecord.add(attributes[2]);

        // Preprocess attribute 4 (tumor-size)
        processedRecord.add(attributes[3]);

        // Preprocess attribute 5 (inv-nodes)
        processedRecord.add(attributes[4]);

        // Preprocess attribute 6 (node-caps)
        processedRecord.add(attributes[5]);

        // Preprocess attribute 7 (deg-malig)
        processedRecord.add(attributes[6]);

        // Preprocess attribute 8 (breast)
        processedRecord.add(attributes[7]);

        // Preprocess attribute 9 (breast-quad)
        processedRecord.add(attributes[8]);

        // Preprocess attribute 10 (irradiat)
        processedRecord.add(attributes[9]);

        return processedRecord;
    }

   
}
