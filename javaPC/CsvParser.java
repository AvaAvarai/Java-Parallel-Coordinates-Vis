package javaPC;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;

public class CsvParser {
    protected static File loadCSVFile() {
        // Create a file chooser dialog
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a dataset file to load");

        // open to datasets folder
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir") + "\\datasets"));

        // Show the dialog and wait for user selection
        int result = fileChooser.showOpenDialog(null);

        // If a file is selected, process it
        if (result != JFileChooser.APPROVE_OPTION) {
            return null;
        }

        File selectedFile = fileChooser.getSelectedFile();
        
        // Check for CSV extension
        String fileName = selectedFile.getName();
        int extensionIndex = fileName.lastIndexOf(".");

        if (extensionIndex > 0 && extensionIndex < fileName.length() - 1) {
            String fileExtension = fileName.substring(extensionIndex + 1).toLowerCase();
            if (fileExtension.equals("csv")) {
                return selectedFile;
            }
        }
        return null;
    }

    protected static String[][] parseCSVFile(File csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            int numRows = getNumRows(csvFile); // Get the number of rows in the CSV file
            int numCols = getNumCols(csvFile); // Get the number of columns in the CSV file
            String[][] data = new String[numRows][numCols];

            // parses CSV into 2D array
            int row = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                for (int col = 0; col < values.length; col++) {
                    data[row][col] = values[col];
                }
                row++;
            }

            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Helper method to get the number of rows in the CSV file
    protected static int getNumRows(File csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int numRows = 0;
            while (br.readLine() != null) {
                numRows++;
            }
            return numRows;
        }
    }

    // Helper method to get the number of columns in the CSV file
    protected static int getNumCols(File csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();
            String[] values = line.split(",");
            return values.length;
        }
    }
}
