import lombok.SneakyThrows;
import moe.haishin.engine.HaishinEngine;
import moe.haishin.engine.input.*;
import org.junit.jupiter.api.Test;

import java.io.File;

public class InputTest {
    @SneakyThrows
    @Test
    public void test() {
        HaishinEngine engine = HaishinEngine.init(null, new File("assets"), "Haishin Engine Test");
        InputManager manager = engine.getInputManager();

        manager.addInputListeners(new AbstractInputListener() {
            @Override
            public void buttonUp(Input input, ButtonCode buttonCode) {
                System.out.println("Button Up pressed " + buttonCode.name());
            }
        });

        Input input = manager.getKeyboard();
        for (InputListener listener : manager.getListeners()) {
            listener.buttonUp(input, ButtonCode.A);
        }
    }
}
