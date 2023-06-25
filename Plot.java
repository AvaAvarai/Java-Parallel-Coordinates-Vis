import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;


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
        int panelHeight = getHeight() - 20;

        int numberOfLines = data[0].length;
        int lineSpacing = panelWidth / (numberOfLines + 1);

        g.setColor(Color.BLACK);

        float[] maxes = new float[numberOfLines - 1];
        for (int j = 1; j < data.length; j++) {
            for (int i = 0; i < numberOfLines - 1; i++) {
                Float dataPnt = Float.parseFloat(data[j][i]);
                if (dataPnt > maxes[i]) {
                    maxes[i] = dataPnt;
                }
            }
        }
        
        for (int i = 1; i <= numberOfLines; i++) {
            int x = lineSpacing * i;
            g.drawLine(x, 20, x, panelHeight); // axis
        }

        for (int j = 1; j < data.length; j++) {
            if (j == numberOfLines) continue;
            for (int i = 0; i < numberOfLines - 1; i++) {
                int x = lineSpacing * (i + 1);
                Float dataPnt = Float.parseFloat(data[j][i]);

                int pos = Math.round(dataPnt * ((panelHeight - 40)) / maxes[i]);

                g.drawOval(x - 3, panelHeight - pos + 20 - 3, 6, 6);
                
                if (i < numberOfLines - 2) {
                    Float nextDataPnt = Float.parseFloat(data[j][i + 1]);
                    int nextPos = Math.round(nextDataPnt * ((panelHeight - 40)) / maxes[i + 1]);
                    g.drawLine(x, panelHeight - pos + 20, lineSpacing * (i + 2), panelHeight - nextPos + 20);
                }
            }
        }
    }
}
