package javaPC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThemedColorPicker extends JDialog {
    private Color selectedColor;

    public ThemedColorPicker(Frame parent) {
        super(parent, "Grayscale Palette", true);
        setSize(500, 200); // Adjusted size for more colors
        setLocationRelativeTo(parent);

        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new GridLayout(1, 15, 10, 10)); // Single row layout for grayscale tones

        // Predefined grayscale colors with more range
        int[] grayscaleValues = {0, 25, 50, 75, 100, 125, 150, 175, 200, 225, 240, 245, 250, 255};

        // Add grayscale color buttons to the panel
        for (int value : grayscaleValues) {
            Color color = new Color(value, value, value);
            JButton colorButton = new JButton();
            colorButton.setBackground(color);
            colorButton.setPreferredSize(new Dimension(30, 30)); // Adjusted button size
            colorButton.setBorderPainted(false); // Remove border
            colorButton.setFocusPainted(false); // Remove focus border
            colorButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    selectedColor = color;
                    dispose(); // Close the dialog when a color is selected
                }
            });
            colorPanel.add(colorButton);
        }

        add(colorPanel, BorderLayout.CENTER);
    }

    // Method to display the color picker and return the selected color
    public Color pickColor() {
        setVisible(true);
        return selectedColor;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(600, 200); // Adjusted frame size
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("Pick Color");
        button.setPreferredSize(new Dimension(150, 50)); // Adjusted button size
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ThemedColorPicker colorPicker = new ThemedColorPicker(frame);
                Color selectedColor = colorPicker.pickColor();
                if (selectedColor != null) {
                    JOptionPane.showMessageDialog(frame, "Selected Color: " + selectedColor);
                }
            }
        });
        frame.add(button, BorderLayout.CENTER);

        frame.setVisible(true);
    }
}
