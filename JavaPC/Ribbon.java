package JavaPC;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Ui ribbon class
 */
public class Ribbon extends JPanel {

    File loadedCSV;

    public Ribbon(Window parent) {
        // Set the preferred size of the panel
        setPreferredSize(new Dimension(250, 50));

        // Set the layout manager to GridBagLayout
        setLayout(new GridBagLayout());

        // Set the background color to a metallic color
        setBackground(new Color(192, 192, 192));

        // Create the buttons
        JButton button1 = new JButton("Load CSV");
        JButton button2 = new JButton("Render Plot");

        // Set the preferred width of the buttons
        button1.setPreferredSize(new Dimension(100, button1.getPreferredSize().height));
        button2.setPreferredSize(new Dimension(100, button2.getPreferredSize().height));

        // Add action listener to the "Load CSV" button
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                loadedCSV = loadCSVFile();
                if (loadedCSV == null) {
                    // Show a basic information message dialog
                    JOptionPane.showMessageDialog(null, "The file selected is not a CSV, please try again.");
                    button2.setEnabled(false);
                } else {
                    button2.setEnabled(true);
                }
        }});

        button2.setEnabled(false);

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                String[][] data = parseCSVFile(loadedCSV);
                parent.render(data);
        }});

        // Add the buttons to the panel with center alignment and left anchor
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(0, 10, 0, 0);

        // Add the buttons to the panel
        add(button1, constraints);

        constraints.gridx = 1;

        add(button2, constraints);

        setVisible(true);
    }

    public File loadCSVFile() {
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

    public String[][] parseCSVFile(File csvFile) {
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
    private static int getNumRows(File csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            int numRows = 0;
            while (br.readLine() != null) {
                numRows++;
            }
            return numRows;
        }
    }

    // Helper method to get the number of columns in the CSV file
    private static int getNumCols(File csvFile) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line = br.readLine();
            String[] values = line.split(",");
            return values.length;
        }
    }
}
