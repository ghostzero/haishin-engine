## About

The Haishin Engine is a self-developed game engine for Java based games. It's known for its unique design for
retro games rendered in a 128x64 canvas. The engine is modular and has several features such as music, scene, scheduler
and input management APIs. With a component like scene management, where each scene has its own lifecycle.

## Java SDK Installation

```
sudo apt update && sudo apt upgrade -y
sudo apt-get install openjdk-17-jdk
```

## Local Development

```
./gradlew publishToMavenLocal
```

## Example

```java
package moe.haishin.engine;

import lombok.SneakyThrows;
import moe.haishin.engine.input.*;
import moe.haishin.engine.scene.Scene;

import java.io.File;

public class App extends Scene {
    @SneakyThrows
    public static void main(String[] args) {
        HaishinEngine engine = HaishinEngine.init(null, new File("assets"), "Haishin Engine Test");
        InputManager manager = engine.getInputManager();

        manager.addInputListeners(new AbstractInputListener() {
            @Override
            public void buttonUp(Input input, ButtonCode buttonCode) {
                System.out.println("Button Up pressed " + buttonCode.name());
            }

            @Override
            public void buttonDown(Input input, ButtonCode buttonCode) {
                System.out.println("Button Down pressed " + buttonCode.name());
            }
        });

        Input input = manager.getKeyboard();
        for (InputListener listener : manager.getListeners()) {
            listener.buttonUp(input, ButtonCode.A);
            listener.buttonUp(input, ButtonCode.A);
            listener.buttonUp(input, ButtonCode.A);
        }

        engine.start(new App());
    }

    @Override
    public void render(HaishinCanvas c) {
        c.clear();
        c.drawString(0, 5, "Tick: " + Haishin.getScheduler().getCurrentTick());
    }
}
```