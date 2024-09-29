package moe.haishin.engine;

import moe.haishin.engine.scene.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HaishinSceneManager {
    private final HaishinEngine game;
    private Scene currentScene = null;
    private Scene nextScene = null;
    private static final Logger log = LoggerFactory.getLogger(HaishinSceneManager.class);

    public HaishinSceneManager(HaishinEngine game) {
        this.game = game;
    }

    void handleChangeScene() throws Exception {
        if (nextScene == null) {
            return;
        }
        log.debug("Requested scene change to " + nextScene.getClass().getSimpleName());
        if (currentScene != null) {
            game.getInputManager().removeInputListeners(currentScene);
            currentScene.unmounted();
            game.getMusicManager().stopAllLoops();
        }
        currentScene = nextScene;
        nextScene = null;
        currentScene.mounted();
        game.getInputManager().addInputListeners(currentScene);
    }

    public void requestNextScene(Scene nextScene) {
        this.nextScene = nextScene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
