package moe.haishin.engine;

import javax.swing.*;

public class HaishinCanvasEngine implements Runnable {
    private final HaishinSceneManager sceneManager;
    private final HaishinCanvas canvas;
    private final JPanel panel;

    public HaishinCanvasEngine(HaishinSceneManager sceneManager, HaishinCanvas canvas, JPanel panel) {
        this.sceneManager = sceneManager;
        this.canvas = canvas;
        this.panel = panel;
    }

    @Override
    public void run() {
        sceneManager.getCurrentScene().render(canvas);
        panel.repaint();
    }
}
