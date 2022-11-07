package moe.haishin.engine.input;

import com.badlogic.gdx.controllers.Controller;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

public class ControllerInput extends AbstractControllerListener implements Input {
    private final InputManager inputManager;

    public ControllerInput(InputManager inputManager, SDL2ControllerManager controllerManager) {
        this.inputManager = inputManager;
        controllerManager.addListener(this);
    }

    @Override
    public boolean buttonUp(Controller controller, int controllerButtonCode) {
        for (InputListener listener : inputManager.getInputListeners()) {
            ButtonCode buttonCode = ButtonCode.mapControllerCode(controllerButtonCode);
            if (buttonCode != null) {
                listener.buttonUp(this, buttonCode);
            }
        }

        return false;
    }

    @Override
    public boolean buttonDown(Controller controller, int controllerButtonCode) {
        for (InputListener listener : inputManager.getInputListeners()) {
            ButtonCode buttonCode = ButtonCode.mapControllerCode(controllerButtonCode);
            if (buttonCode != null) {
                listener.buttonDown(this, buttonCode);
            }
        }

        return false;
    }
}
