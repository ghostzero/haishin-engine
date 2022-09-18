package moe.haishin.engine;

import moe.haishin.engine.scene.Scene;

public class HaishinSceneManager {
    private final HaishinEngine game;
    private Scene currentScene = null;
    private Scene nextScene = null;

    public HaishinSceneManager(HaishinEngine game) {
        this.game = game;
    }

    void handleChangeScene() throws Exception {
        if (nextScene == null) {
            return;
        }
        System.out.println("Requested scene change to " + nextScene.getClass().getSimpleName());
        if (currentScene != null) {
            game.getControllerManager().removeListener(currentScene);
            currentScene.unmounted();
            game.getMusicManager().stopAllLoops();
        }
        currentScene = nextScene;
        nextScene = null;
        currentScene.mounted();
        game.getControllerManager().addListenerAndRunForConnectedControllers(currentScene);
    }

    public void requestNextScene(Scene nextScene) {
        this.nextScene = nextScene;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }
}
