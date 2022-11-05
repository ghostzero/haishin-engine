package moe.haishin.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class HaishinPanel extends JPanel {
    private final HaishinCanvas canvas;
    private BufferedImage overlay;

    public HaishinPanel(HaishinCanvas canvas, float scale) throws IOException {
        this.canvas = canvas;

        Dimension expectedDimension = new Dimension((int) (canvas.getWidth() * scale), (int) (canvas.getHeight() * scale));

        setPreferredSize(expectedDimension);
        setMaximumSize(expectedDimension);
        setMinimumSize(expectedDimension);
        setBackground(Color.BLACK);
    }

    public void setOverlay(BufferedImage overlay) {
        this.overlay = overlay;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(canvas.paintComponent(), 0, 0, getWidth(), getHeight(), null);
        if (overlay != null) {
            g.drawImage(overlay, 0, 0, getWidth(), getHeight(), 0, 0, getWidth(), getHeight(), null);
        }
    }
}
