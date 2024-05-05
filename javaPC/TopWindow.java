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

import java.util.HashMap;

/**
 * Window container class
 */
public class TopWindow extends JFrame {

    protected static final String TITLE = "Java Parallel Coordinates Visualization Tool";

    private static final int WIDTH = 1600;
    private static final int HEIGHT = 800;

    private PlotPanel pcPlot;

    protected TopWindow() {
        // Initialize the window settings
        setTitle(TITLE);
        setResizable(false);
        getContentPane().setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components
        UiRibbon ribbon = new UiRibbon(this);
        JPanel bottomPanel = createBottomPanel(ribbon);
        add(bottomPanel, BorderLayout.SOUTH);

        JPanel prompt = createCenterPrompt();
        add(prompt, BorderLayout.CENTER);

        init();
    }

    private JPanel createBottomPanel(UiRibbon ribbon) {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Color.LIGHT_GRAY);
        bottomPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(0, 0, 0, 0);

        bottomPanel.add(ribbon, gbc);
        return bottomPanel;
    }

    private JPanel createCenterPrompt() {
        JLabel prompt = new JLabel("Please load a dataset with the 'Load CSV' toolbar button below.");
        prompt.setFont(new Font("Arial", Font.PLAIN, 16));
        JPanel innerPanel = new JPanel();
        innerPanel.add(prompt);
        innerPanel.setBorder(new EmptyBorder(300, 0, 300, 0));
        return innerPanel;
    }

    protected void init() {
        pack();
        setVisible(true);
    }

    protected void render(String dataset, String[][] data) {
        if (pcPlot != null) {
            remove(pcPlot);
        }

        updateTitleWithClassCounts(dataset, data);
        pcPlot = new PlotPanel(data);
        add(pcPlot, BorderLayout.CENTER);
        init();
    }

    private void updateTitleWithClassCounts(String dataset, String[][] data) {
        HashMap<String, Integer> classCounts = countClasses(data);
        StringBuilder casesPerClass = new StringBuilder();
        for (String key : classCounts.keySet()) {
            casesPerClass.append(key + ": " + classCounts.get(key) + ", ");
        }

        if (casesPerClass.length() > 0) {
            casesPerClass.setLength(casesPerClass.length() - 2);
        }

        setTitle(TITLE + " - " + dataset + " - " + (data.length - 1) + " Cases - [" + casesPerClass.toString() + "]");
    }

    private HashMap<String, Integer> countClasses(String[][] data) {
        HashMap<String, Integer> classCounts = new HashMap<>();
        for (int i = 1; i < data.length; i++) {
            String className = data[i][data[i].length - 1];
            classCounts.put(className, classCounts.getOrDefault(className, 0) + 1);
        }
        return classCounts;
    }

    public PlotPanel getPlotPanel() {
        return pcPlot;
    }

    public void toggleAxisNames() {
        if (pcPlot != null) {
            pcPlot.setShowAxisNames(!pcPlot.isShowingAxisNames());
            pcPlot.repaint();
        }
    }
}
    