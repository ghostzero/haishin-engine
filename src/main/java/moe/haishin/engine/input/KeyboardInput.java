package moe.haishin.engine.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Set;
import java.util.TreeSet;

public class KeyboardInput implements Input, KeyListener {
    private static final Logger log = LoggerFactory.getLogger(KeyboardInput.class);
    private final InputManager inputManager;
    private final Set<Integer> pressedKeys = new TreeSet<>();

    public KeyboardInput(InputManager inputManager, JFrame frame) {
        this.inputManager = inputManager;
        frame.addKeyListener(this);
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (pressedKeys.contains(e.getKeyCode())) {
            return;
        }
        pressedKeys.add(e.getKeyCode());
        for (InputListener listener : inputManager.getInputListeners()) {
            ButtonCode buttonCode = ButtonCode.mapKeyEvent(e);
            if (buttonCode != null) {
                listener.buttonDown(this, buttonCode);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
        for (InputListener listener : inputManager.getInputListeners()) {
            ButtonCode buttonCode = ButtonCode.mapKeyEvent(e);
            if (buttonCode != null) {
                listener.buttonUp(this, buttonCode);
            }
        }
    }
}
