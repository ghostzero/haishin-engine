package moe.haishin.engine.scene;

import moe.haishin.engine.HaishinCanvas;
import moe.haishin.engine.input.ButtonCode;
import moe.haishin.engine.input.Input;
import moe.haishin.engine.input.InputListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Scenes allow us to split the Game into independent and reusable pieces,
 * and think about each piece in isolation.
 */
public abstract class Scene implements InputListener {
    private static final Logger log = LoggerFactory.getLogger(Scene.class);

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
    public void connected(Input input) {
        log.debug("*** CONNECTED " + input);
    }

    @Override
    public void disconnected(Input input) {
        log.debug("*** DISCONNECTED " + input);
    }

    @Override
    public void buttonDown(Input input, ButtonCode buttonCode) {
        log.debug("BUTTON DOWN " + input + " " + buttonCode);
    }

    @Override
    public void buttonUp(Input input, ButtonCode buttonCode) {
        log.debug("BUTTON DOWN " + input + " " + buttonCode);
    }
}
