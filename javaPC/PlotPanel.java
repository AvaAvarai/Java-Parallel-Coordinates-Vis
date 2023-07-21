package javaPC;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Plot renderer class
 */
public class PlotPanel extends JPanel {

    private static final int WIDTH = 1600;
    private static final int HEIGHT = 750;

    private HashMap<String, Color> colorMap;

    private String[][] data;

    protected PlotPanel(String[][] data) {
        this.data = data;

        // Set the preferred size of the panel
        setPreferredSize(new Dimension(WIDTH, HEIGHT));

        ArrayList<String> classNames = new ArrayList<>();
        for (int j = 1; j < data.length; j++) {
            for (int i = 0; i < data[0].length; i++) {
                if (i == data[0].length - 1) {
                    String name = data[j][i];
                    if (!classNames.contains(name)) {
                        classNames.add(name);
                    }
                    continue;
                }
            }
        }       

        colorMap = new HashMap<>();
        for (String name : classNames) {
             // Generate random RGB values
            int red = (int) (Math.random() * 255);
            int green = (int) (Math.random() * 255);
            int blue = (int) (Math.random() * 255);

            // Create a random color
            Color randomColor = new Color(red, green, blue);

            // Set the color of the graphics object
            colorMap.put(name, randomColor);
        }
        
        // Set the background color to light gray
        setBackground(Color.WHITE);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int margin = 70;

        int axisCount = data[0].length;
        int lineSpacing = panelWidth / (axisCount + 1);

        g.setColor(Color.BLACK);

        float[] maxes = new float[axisCount - 1]; // init to all 0s
        float[] mins = new float[axisCount - 1]; // init to all max values
        Arrays.fill(mins, Float.MAX_VALUE);

        for (int j = 1; j < data.length; j++) {
            for (int i = 0; i < axisCount - 1; i++) {
                Float dataPnt = Float.parseFloat(data[j][i]);
                if (dataPnt > maxes[i]) {
                    maxes[i] = dataPnt;
                }
                if (dataPnt < mins[i]) {
                    mins[i] = dataPnt;
                }
            }
        }

        // draw axis lines
        for (int i = 1; i <= axisCount; i++) {
            int x = lineSpacing * i;
            g.drawLine(x, 35, x, panelHeight - margin);
        }
        
        HashMap<String, Integer> classNums = new HashMap<>();
        int next = 0;
        for (int j = 1; j < data.length; j++) {
            for (int i = 1; i < axisCount; i++) {
                int x = lineSpacing * i;
                g.setColor(colorMap.get(data[j][axisCount - 1]));
                // n datapoint
                Float dataPnt = Float.parseFloat(data[j][i-1]);
                int pos = Math.round((panelHeight + 35 - margin - margin) * ((dataPnt - mins[i-1]) / (maxes[i-1] - mins[i-1])) + margin);
                if (i < axisCount - 1) {
                    // n+1 datapoint
                    Float nextDataPnt = Float.parseFloat(data[j][i]);
                    int nextPos = Math.round((panelHeight + 35 - margin - margin) * ((nextDataPnt - mins[i]) / (maxes[i] - mins[i])) + margin);
                    // draw n datapoint
                    g.fillOval(x - 3, panelHeight - pos - 3, 6, 6);
                    // connect n to n+1 datapoints with an edge
                    g.drawLine(x, panelHeight - pos, lineSpacing * (i + 1), panelHeight - nextPos);
                } else if (i == axisCount - 1){
                    String className = data[j][i];
                    
                    if (classNums.containsKey(className)) {
                        int nextDataPnt = (panelHeight + 35 - margin - margin) * classNums.get(className) / colorMap.size() + margin;
                        g.fillOval(x - 3, panelHeight - pos - 3, 6, 6);
                        g.drawLine(x, panelHeight - pos, lineSpacing * (i + 1), panelHeight - nextDataPnt);
                        JLabel label = new JLabel(className);
                        int xPos = lineSpacing * (i+1) + className.length() * 4;
                        xPos -= (int)Math.floor(2*className.length());
                        int yPos = panelHeight - nextDataPnt - 10;
                        Point position = new Point(xPos, yPos);
                        label.setLocation(position);

                        // Set the size of the label
                        int width = 150;
                        int height = 15;
                        Dimension size = new Dimension(width, height);
                        label.setSize(size);
                        add(label);
                    } else {
                        classNums.put(className, next);
                        next++;
                    }
                }
            }
        }

        // draw axis labels
        for (int i = 1; i <= axisCount; i++) {
            String name = data[0][i-1];
            JLabel label = new JLabel(name);
            // Set the position of the label
            int xPos = lineSpacing * i;
            xPos -= (int)Math.floor(3*name.length());
            int yPos = 5;
            Point position = new Point(xPos, yPos);
            label.setLocation(position);

            // Set the size of the label
            int width = 150;
            int height = 15;
            Dimension size = new Dimension(width, height);
            label.setSize(size);
            add(label);

            if (i < axisCount) {
                // label float formatting
                DecimalFormat formatter = new DecimalFormat("0.##");
                String maxName = formatter.format(maxes[i-1]);
                
                // Max axis value label
                JLabel maxLabel = new JLabel(maxName);

                // label position
                int maxXPos = lineSpacing * i;
                maxXPos -= (int)Math.floor(3*maxName.length());
                int maxYPos = 18;
                Point maxPosition = new Point(maxXPos, maxYPos);
                maxLabel.setLocation(maxPosition);

                // label size
                int maxWidth = 50;
                int maxHeight = 15;
                Dimension maxSize = new Dimension(maxWidth, maxHeight);
                maxLabel.setSize(maxSize);
                add(maxLabel);

                // min axis value label
                String minName = formatter.format(mins[i-1]);
                JLabel minLabel = new JLabel(minName);

                // label position
                int minXPos = lineSpacing * i;
                minXPos -= (int)Math.floor(3*minName.length());
                int minYPos = panelHeight - 65;
                Point minPosition = new Point(minXPos, minYPos);
                minLabel.setLocation(minPosition);

                // label size
                int minWidth = 50;
                int minHeight = 15;
                Dimension minSize = new Dimension(minWidth, minHeight);
                minLabel.setSize(minSize);
                add(minLabel);
            }
        }
    }
}
