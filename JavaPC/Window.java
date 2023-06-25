package JavaPC;

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
public class Window extends JFrame {

    public Window() {
        // Set the title of the frame
        setTitle("JavaPC v0");

        setResizable(false);

        // Set the size of the frame
        getContentPane().setPreferredSize(new Dimension(1600, 800));

        // Set the layout manager to FlowLayout
        setLayout(new BorderLayout());

        // Set the background color to bright green
        getContentPane().setBackground(Color.DARK_GRAY);

        // Exit the application when the frame is closed
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Ribbon ribbon = new Ribbon(this);

        JPanel bottomPanel = new JPanel(); // Create a separate panel for the uiPanel
        bottomPanel.setBackground(Color.darkGray); // Adjust the color as needed
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
        prompt.setFont(new Font("Arial", Font.PLAIN, 12));
    
        // Create an inner JPanel with FlowLayout and add the label
        JPanel innerPanel = new JPanel();
        innerPanel.add(prompt);
        innerPanel.setBorder(new EmptyBorder(300, 0, 300, 0)); // Add empty border for centering
        add(innerPanel, BorderLayout.CENTER);

        init();
    }

    public void init() {
        pack();
        setVisible(true);
    }

    public void render(String[][] data) {
        Plot pcPlot = new Plot(data);
        add(pcPlot, BorderLayout.CENTER);
        setVisible(true);
    }
}
