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
public class Plot extends JPanel {

    String dataset;
    String[][] data;

    public Plot(String dataset, String[][] data) {
        this.dataset = dataset;
        this.data = data;

        // Set the preferred size of the panel
        setPreferredSize(new Dimension(1600, 750));
        
        // Set the background color to light gray
        setBackground(Color.LIGHT_GRAY);

        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int panelWidth = getWidth();
        int panelHeight = getHeight();
        int margin = 50;

        int axisCount = data[0].length;
        int lineSpacing = panelWidth / (axisCount + 1);

        ArrayList<String> classNames = new ArrayList<>();

        g.setColor(Color.BLACK);

        float[] maxes = new float[axisCount - 1]; // init to all 0s
        float[] mins = new float[axisCount - 1]; // init to all max values
        Arrays.fill(mins, Float.MAX_VALUE);

        // TODO: Move to a preprocessing step.
        for (int j = 1; j < data.length; j++) {
            for (int i = 0; i < axisCount; i++) {
                if (i == axisCount - 1) {
                    String name = data[j][i];
                    if (!classNames.contains(name)) {
                        classNames.add(name);
                    }
                    continue;
                }

                Float dataPnt = Float.parseFloat(data[j][i]);
                if (dataPnt > maxes[i]) {
                    maxes[i] = dataPnt;
                }
                if (dataPnt < mins[i]) {
                    mins[i] = dataPnt;
                }
            }
        }

        HashMap<String, Color> colorMap = new HashMap<>();
        for (String name : classNames) {
             // Generate random RGB values
            int red = (int) (Math.random() * 256);
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);

            // Create a random color
            Color randomColor = new Color(red, green, blue);

            // Set the color of the graphics object
            colorMap.put(name, randomColor);
        }

        // draw axis lines
        for (int i = 1; i <= axisCount; i++) {
            int x = lineSpacing * i;
            g.drawLine(x, margin, x, panelHeight - margin);
        }
        
        for (int j = 1; j < data.length; j++) {
            for (int i = 1; i < axisCount; i++) {
                int x = lineSpacing * i;
                Float dataPnt = Float.parseFloat(data[j][i-1]);
                int pos = Math.round((panelHeight - margin - margin) * ((dataPnt - mins[i-1]) / (maxes[i-1] - mins[i-1])) + margin);

                g.setColor(colorMap.get(data[j][axisCount - 1]));

                g.fillOval(x - 3, panelHeight - pos - 3, 6, 6);
                
                if (i < axisCount - 1) {
                    Float nextDataPnt = Float.parseFloat(data[j][i]);
                    int nextPos = Math.round((panelHeight - margin - margin) * ((nextDataPnt - mins[i]) / (maxes[i] - mins[i])) + margin);
                    g.drawLine(x, panelHeight - pos, lineSpacing * (i + 1), panelHeight - nextPos);
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
            int yPos = 10;
            Point position = new Point(xPos, yPos);
            label.setLocation(position);

            // Set the size of the label
            int width = 100;
            int height = 30;
            Dimension size = new Dimension(width, height);
            label.setSize(size);
            add(label);

            if (i < axisCount) {
                DecimalFormat formatter = new DecimalFormat("#0.00");
                String maxName = formatter.format(maxes[i-1]);
                
                JLabel maxLabel = new JLabel(maxName);
                // Set the position of the label
                int maxXPos = lineSpacing * i;
                maxXPos -= (int)Math.floor(3*maxName.length());
                int maxYPos = 25;
                Point maxPosition = new Point(maxXPos, maxYPos);
                maxLabel.setLocation(maxPosition);

                // Set the size of the label
                int maxWidth = 100;
                int maxHeight = 30;
                Dimension maxSize = new Dimension(maxWidth, maxHeight);
                maxLabel.setSize(maxSize);
                add(maxLabel);

                String minName = formatter.format(mins[i-1]);
                JLabel minLabel = new JLabel(minName);
                // Set the position of the label
                int minXPos = lineSpacing * i;
                minXPos -= (int)Math.floor(3*minName.length());
                int minYPos = panelHeight - 50;
                Point minPosition = new Point(minXPos, minYPos);
                minLabel.setLocation(minPosition);

                // Set the size of the label
                int minWidth = 100;
                int minHeight = 30;
                Dimension minSize = new Dimension(minWidth, minHeight);
                minLabel.setSize(minSize);
                add(minLabel);
            }
        }

        JLabel label = new JLabel("Dataset: " + dataset + " Sample count: " + (data.length-1));

        // Set the size of the label
        int width = 500;
        int height = 30;
        Dimension size = new Dimension(width, height);
        label.setSize(size);

        // Set the position of the label
        int xPos = 10;
        int yPos = panelHeight - height;
        Point position = new Point(xPos, yPos);
        label.setLocation(position);

        add(label);
    }
}
