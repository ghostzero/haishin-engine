package moe.haishin.engine;

import moe.haishin.engine.font.FontLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

public class HaishinCanvas {
    protected final BufferedImage bufferedImage;
    private final HaishinEngine game;
    private final int width;
    private final int height;
    private final Font fontVisitor;

    public HaishinCanvas(HaishinEngine game, int x, int y) throws Exception {
        this.game = game;
        this.width = x;
        this.height = y;
        this.bufferedImage = new BufferedImage(x, y, BufferedImage.TYPE_INT_ARGB);
        this.fontVisitor = FontLoader.register("/fonts/visitor1.ttf");
    }

    public void setPixel(int x, int y, Color color) {
        bufferedImage.setRGB(x, y, color.getRGB());
    }

    public void setPixels(int x, int y, int width, int height, Color color) {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                setPixel(x + i, y + j, color);
            }
        }
    }

    public void drawImage(int x, int y, String name) {
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(game.getImageManager().get(name), x, y, null);
        g.dispose();
    }

    public void drawImage(int x, int y, int width, int height, String name) {
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(game.getImageManager().get(name), x, y, width, height, null);
        g.dispose();
    }

    public Image paintComponent() {
        if (!game.isDebug()) {
            return bufferedImage;
        }

        // draw debug information
        // string format float with two decimal places
        drawImage(0, 0, "bg-dialog");
        drawString(-1, 18, String.format("Music: %.2f", game.getMusicManager().getVolume()));
        for (int i = 0; i < game.getThreads().size(); i++) {
            HaishinThread thread = game.getThreads().get(i);
            drawString(-1, 24 + (i * 6), thread.getName() + ": " + thread.getFps());
        }

        return bufferedImage;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void drawString(int x, int y, String string) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.setFont(fontVisitor.deriveFont(Font.PLAIN, 10));
        x = x == -1 ? (width - g.getFontMetrics().stringWidth(string)) / 2 :
                (x < -1 ? (width - g.getFontMetrics().stringWidth(string)) + x : x);
        y = y == -1 ? ((height - g.getFontMetrics().getHeight()) / 2) + 8 : y;
        g.drawString(string, x, y);
        g.dispose();
    }

    public void drawStringWithCursor(int x, int y, String string) {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.WHITE);
        g.setFont(fontVisitor.deriveFont(Font.PLAIN, 10));
        int fontWidth = g.getFontMetrics().stringWidth(string);
        x = x == -1 ? (width - fontWidth) / 2 : x;
        y = y == -1 ? ((height - g.getFontMetrics().getHeight()) / 2) + 8 : y;
        g.drawString(string, x, y);

        if (System.currentTimeMillis() / 1000 % 2 == 0) {
            g.drawString("_", x + fontWidth - 6, y);
        }

        g.dispose();
    }

    public void clear() {
        Graphics2D g = bufferedImage.createGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        g.dispose();
    }
}
