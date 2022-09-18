package moe.haishin.engine;

import moe.haishin.engine.api.ApiService;
import moe.haishin.engine.font.FontLoader;
import moe.haishin.engine.image.ImageManager;
import moe.haishin.engine.music.MusicManager;
import moe.haishin.engine.scheduler.SchedulerManager;

public class Haishin {
    private static HaishinEngine engine;

    /**
     * Static class cannot be initialized.
     */
    private Haishin() {
    }

    public static HaishinEngine getEngine() {
        return engine;
    }

    public static void setEngine(HaishinEngine engine) {
        if (Haishin.engine != null)
            throw new IllegalStateException("HaishinEngine is already set.");

        Haishin.engine = engine;
    }

    public static SchedulerManager getScheduler() {
        return engine.getScheduler();
    }

    public static MusicManager getMusicManager() {
        return engine.getMusicManager();
    }

    public static ApiService getApiService() {
        return engine.getApiService();
    }

    public static boolean isDebug() {
        return engine.isDebug();
    }

    public static HaishinSceneManager getSceneManager() {
        return engine.getSceneManager();
    }

    public static ImageManager getImageManager() {
        return engine.getImageManager();
    }
}
