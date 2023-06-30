package javaPC;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.BorderLayout;

/**
 * Window container class
 */
public class TopWindow extends JFrame {

    private static final String TITLE = "JavaPC v0.0 - Parallel Coordinates";

    private static final int WIDTH = 1600;
    private static final int HEIGHT = 800;

    private PlotPanel pcPlot;

    protected TopWindow() {
        // Set the title of the frame
        setTitle(TITLE);

        setResizable(false);

        // Set the size of the frame
        getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));

        // Set the layout manager to FlowLayout
        setLayout(new BorderLayout());
        
        getContentPane().setBackground(Color.WHITE);

        // Exit the application when the frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        UiRibbon ribbon = new UiRibbon(this);

        JPanel bottomPanel = new JPanel(); // Create a separate panel for the uiPanel
        bottomPanel.setBackground(Color.LIGHT_GRAY); // Adjust the color as needed
        bottomPanel.setLayout(new GridBagLayout()); // Use GridBagLayout for the bottom panel

        // Create a GridBagConstraints instance to control the layout of components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; // Set the grid x position to 0
        gbc.gridy = 0; // Set the grid y position to 0
        gbc.anchor = GridBagConstraints.CENTER; // Center-align the component
        gbc.insets = new Insets(0, 0, 0, 0); // Add some spacing around the component

        bottomPanel.add(ribbon, gbc); // Add the uiPanel using the GridBagConstraints

        add(bottomPanel, BorderLayout.SOUTH); // Add the bottom panel to the SOUTH position

        JLabel prompt = new JLabel("Please load a dataset with 'Load CSV' on UI ribbon below.");
        prompt.setFont(new Font("Arial", Font.PLAIN, 14));
    
        // Create an inner JPanel with FlowLayout and add the label
        JPanel innerPanel = new JPanel();
        innerPanel.add(prompt);
        innerPanel.setBorder(new EmptyBorder(300, 0, 300, 0)); // Add empty border for centering
        add(innerPanel, BorderLayout.CENTER);

        init();
    }

    protected void init() {
        pack();
        setVisible(true);
    }

    protected void render(String dataset, String[][] data) {
        if (pcPlot != null) {
            remove(pcPlot);
        }
        setTitle(TITLE + " - " + dataset + " - " + (data.length - 1) + " Samples");
        pcPlot = new PlotPanel(data);
        add(pcPlot, BorderLayout.CENTER);
        init();
    }
}
