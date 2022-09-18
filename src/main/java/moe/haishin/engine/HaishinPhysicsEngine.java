package moe.haishin.engine;

import org.libsdl.SDL_Error;

public class HaishinPhysicsEngine implements Runnable {
    private final HaishinEngine engine;
    private long tickCounter = 0;

    public HaishinPhysicsEngine(HaishinEngine engine) {
        this.engine = engine;
    }

    @Override
    public void run() {
        try {
            if (engine.isFocus()) {
                engine.getControllerManager().pollState();
            }
        } catch (SDL_Error e) {
            e.printStackTrace();
        }
        ++tickCounter;
        engine.getScheduler().mainThreadHeartbeat(tickCounter);
        try {
            engine.getSceneManager().handleChangeScene();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
