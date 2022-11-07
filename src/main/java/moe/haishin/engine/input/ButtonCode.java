package moe.haishin.engine.input;

import java.awt.event.KeyEvent;

public enum ButtonCode {
    A(0, KeyEvent.VK_A),
    B(1, KeyEvent.VK_B),
    X(2, KeyEvent.VK_X),
    Y(3, KeyEvent.VK_Y),
    SELECT(4, KeyEvent.VK_TAB),
    START(6, KeyEvent.VK_SHIFT),
    L1(9, null),
    R1(10, null),
    UP(11, KeyEvent.VK_UP),
    DOWN(12, KeyEvent.VK_DOWN),
    LEFT(13, KeyEvent.VK_LEFT),
    RIGHT(14, KeyEvent.VK_RIGHT);

    private final Integer controllerButtonCode;
    private final Integer keyCode;

    ButtonCode(Integer controllerButtonCode, Integer keyCode) {
        this.controllerButtonCode = controllerButtonCode;
        this.keyCode = keyCode;
    }

    public Integer getControllerButtonCode() {
        return controllerButtonCode;
    }

    public Integer getKeyCode() {
        return keyCode;
    }

    public static ButtonCode mapControllerCode(int controllerButtonCode) {
        for (ButtonCode buttonCode : ButtonCode.values()) {
            if (buttonCode.getControllerButtonCode() != null
                    && buttonCode.getControllerButtonCode() == controllerButtonCode) {
                return buttonCode;
            }
        }

        return null;
    }

    public static ButtonCode mapKeyEvent(KeyEvent e) {
        for (ButtonCode buttonCode : ButtonCode.values()) {
            if (buttonCode.getKeyCode() != null
                    && buttonCode.getKeyCode() == e.getKeyCode()) {
                return buttonCode;
            }
        }

        return null;
    }
}
