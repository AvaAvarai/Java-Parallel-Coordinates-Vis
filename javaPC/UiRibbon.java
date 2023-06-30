package javaPC;

import javax.swing.JPanel;
import javax.swing.JButton;

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
public class UiRibbon extends JPanel {

    protected static final int WIDTH = 250;
    protected static final int HEIGHT = 30;

    private File loadedCSV;

    protected UiRibbon(TopWindow parent) {
        // Set the preferred size of the panel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Set the layout manager to GridBagLayout
        setLayout(new GridBagLayout());

        // Set the background color to a metallic color
        setBackground(Color.LIGHT_GRAY);

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
                loadedCSV = CsvParser.loadCSVFile();
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
                String[][] data = CsvParser.parseCSVFile(loadedCSV);
                parent.render(loadedCSV.getName(), data);

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
}
