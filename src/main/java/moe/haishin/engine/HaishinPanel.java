package moe.haishin.engine;

import javax.swing.*;
import java.awt.*;

public class HaishinPanel extends JPanel {
    private final HaishinCanvas canvas;

    public HaishinPanel(HaishinCanvas canvas, float scale) {
        this.canvas = canvas;

        Dimension expectedDimension = new Dimension((int) (canvas.getWidth() * scale), (int) (canvas.getHeight() * scale));

        setPreferredSize(expectedDimension);
        setMaximumSize(expectedDimension);
        setMinimumSize(expectedDimension);
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(canvas.paintComponent(), 0, 0, getWidth(), getHeight(), null);
    }
}
