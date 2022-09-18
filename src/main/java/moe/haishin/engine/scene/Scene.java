package moe.haishin.engine.scene;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.math.Vector3;
import moe.haishin.engine.HaishinCanvas;
import moe.haishin.engine.HaishinEngine;

import java.io.IOException;

/**
 * Scenes allow us to split the Game into independent and reusable pieces,
 * and think about each piece in isolation.
 */
public abstract class Scene implements ControllerListener {
    /**
     * Called after the scene has been mounted.
     */
    public void mounted() throws IOException {
        // do nothing
    }

    /**
     * Called after the scene has been unmounted.
     */
    public void unmounted() {
        // do nothing
    }

    /**
     * Called during every game render circle.
     *
     * @param c Canvas that needs to be rendered
     */
    abstract public void render(HaishinCanvas c);

    @Override
    public void connected(Controller controller) {
        System.out.println("*** CONNECTED " + controller);
    }

    @Override
    public void disconnected(Controller controller) {
        System.out.println("*** DISCONNECTED " + controller);
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        System.out.println("BUTTON DOWN " + controller + " " + buttonCode);
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        System.out.println("BUTTON UP " + controller + " " + buttonCode);
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        System.out.println("AXIS MOVED " + controller + " " + axisCode + " " + value);
        return false;
    }

    @Override
    public boolean povMoved(Controller controller, int povCode, PovDirection value) {
        System.out.println("POV MOVED +" + controller + " " + povCode + " " + value.toString());
        return false;
    }

    @Override
    public boolean xSliderMoved(Controller controller, int sliderCode, boolean value) {
        System.out.println("XSLIDER MOVED +" + controller + " " + sliderCode + " " + value);
        return false;
    }

    @Override
    public boolean ySliderMoved(Controller controller, int sliderCode, boolean value) {
        System.out.println("YSLIDER MOVED +" + controller + " " + sliderCode + " " + value);
        return false;
    }

    @Override
    public boolean accelerometerMoved(Controller controller, int accelerometerCode, Vector3 value) {
        System.out.println("ACCELEROMETER MOVED +" + controller + " " + accelerometerCode + " " + value);
        return false;
    }
}
