import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Arrays;


public class Plot extends JPanel {

    String[][] data;

    public Plot(String[][] data) {
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
        int margin = 20;

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

        System.out.println(Arrays.toString(mins));
        
        for (int i = 1; i <= axisCount; i++) {
            int x = lineSpacing * i;
            g.drawLine(x, 20, x, panelHeight - margin); // axis
        }

        for (int j = 1; j < data.length; j++) {
             // Generate random RGB values
            int red = (int) (Math.random() * 256);
            int green = (int) (Math.random() * 256);
            int blue = (int) (Math.random() * 256);

            // Create a random color
            Color randomColor = new Color(red, green, blue);

            // Set the color of the graphics object
            g.setColor(randomColor);

            for (int i = 1; i < axisCount; i++) {
                int x = lineSpacing * i;
                Float dataPnt = Float.parseFloat(data[j][i-1]);

                int pos = Math.round((panelHeight - margin - margin) * ((dataPnt - mins[i-1]) / (maxes[i-1] - mins[i-1])) + margin);

                g.drawOval(x - 3, panelHeight - pos - 3, 6, 6);
                
                if (i < axisCount - 1) {
                    Float nextDataPnt = Float.parseFloat(data[j][i]);
                    int nextPos = Math.round((panelHeight - margin - margin) * ((nextDataPnt - mins[i]) / (maxes[i] - mins[i])) + margin);
                    g.drawLine(x, panelHeight - pos, lineSpacing * (i + 1), panelHeight - nextPos);
                }
            }
        }
    }
}
