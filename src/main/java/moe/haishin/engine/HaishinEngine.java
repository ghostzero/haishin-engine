package moe.haishin.engine;

import moe.haishin.engine.api.ApiService;
import moe.haishin.engine.image.ImageManager;
import moe.haishin.engine.input.*;
import moe.haishin.engine.music.MusicManager;
import moe.haishin.engine.scene.Scene;
import moe.haishin.engine.scheduler.SchedulerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.co.electronstudio.sdl2gdx.SDL2ControllerManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HaishinEngine {
    private static final Logger log = LoggerFactory.getLogger(HaishinEngine.class);
    private static HaishinEngine instance;
    private final HaishinSceneManager sceneManager;
    private final List<HaishinThread> gameThreads = new ArrayList<>();
    private final SDL2ControllerManager controllerManager;
    private final MusicManager musicManager;
    private final ImageManager imageManager;
    private final ApiService apiService;
    private final JFrame frame;
    private final HaishinPanel panel;
    private final InputManager inputManager;
    private boolean debug = false;
    private final SchedulerManager schedulerManager;

    private HaishinEngine(ApiService apiService, File assetDirectory, String title) throws Exception {
        Asset.setBaseDirectory(assetDirectory);
        Haishin.setEngine(this);
        this.apiService = apiService;
        imageManager = new ImageManager();
        HaishinCanvas canvas = new HaishinCanvas(this, 128, 64);

        panel = new HaishinPanel(canvas, 8f);  // 15 = 1920x1080, 10 = 1280x720

        Box box = new Box(BoxLayout.Y_AXIS);
        box.setBackground(Color.BLACK);

        box.add(Box.createVerticalGlue());
        box.add(panel);
        box.add(Box.createVerticalGlue());

        frame = new JFrame(title);

        // load icon from resources if exists
        URL uri = getClass().getResource("/icon.png");
        if (uri != null) {
            frame.setIconImage(new ImageIcon(uri).getImage());
        }

        frame.add(box);
        // get frameborder
        frame.setSize(getDimensionWithFrameBorder(panel.getPreferredSize()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setMinimumSize(frame.getMinimumSize());   // cannot be resized-

        frame.setVisible(true);
        frame.setResizable(false);
        frame.setAlwaysOnTop(true);
        frame.setFocusTraversalKeysEnabled(false);

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                // log the new size
                log.debug("New size: " + e.getComponent().getSize());
            }
        });

        musicManager = new MusicManager();
        sceneManager = new HaishinSceneManager(this);
        schedulerManager = new SchedulerManager(this);

        // initialize inputs
        inputManager = new InputManager();
        inputManager.addInput(new KeyboardInput(inputManager, frame));
        inputManager.addInputListeners(new AbstractInputListener() {
            @Override
            public void buttonUp(Input input, ButtonCode buttonCode) {
                if (buttonCode == ButtonCode.SELECT) {
                    setDebug(!isDebug());
                }

                if (isDebug() && buttonCode == ButtonCode.LEFT) {
                    musicManager.setVolume(musicManager.getVolume() - 0.05f);
                }

                if (isDebug() && buttonCode == ButtonCode.RIGHT) {
                    musicManager.setVolume(musicManager.getVolume() + 0.05f);
                }
            }
        });

        // register all inputs
        //SDL.SDL_SetHint("SDL_XINPUT_ENABLED", "0");
        controllerManager = new SDL2ControllerManager();
        inputManager.addInput(new ControllerInput(inputManager, controllerManager));


        HaishinCanvasEngine canvasEngine = new HaishinCanvasEngine(sceneManager, canvas, panel);
        HaishinPhysicsEngine physicsEngine = new HaishinPhysicsEngine(this);

        gameThreads.add(new HaishinThread(canvasEngine, "Canvas", 60));
        gameThreads.add(new HaishinThread(physicsEngine, "Physics", 20));
    }

    @SuppressWarnings("unused")
    public static HaishinEngine init(ApiService service, File assetDirectory, String title) throws Exception {
        if (instance != null)
            throw new IllegalStateException("Already initialized");

        instance = new HaishinEngine(service, assetDirectory, title);

        return instance;
    }

    private Dimension getDimensionWithFrameBorder(Dimension expectedDimension) {
        if (System.getProperty("os.name").toLowerCase().contains("win")) {
            return new Dimension(expectedDimension.width + 16, expectedDimension.height + 38);
        }
        return new Dimension(expectedDimension.width, expectedDimension.height);
    }

    @SuppressWarnings("unused")
    public void start(Scene initialScene) throws Exception {
        getSceneManager().requestNextScene(initialScene);
        getSceneManager().handleChangeScene();

        for (HaishinThread thread : gameThreads) {
            thread.start();
        }
    }

    public HaishinSceneManager getSceneManager() {
        return sceneManager;
    }

    public MusicManager getMusicManager() {
        return musicManager;
    }

    public ImageManager getImageManager() {
        return imageManager;
    }

    @Deprecated
    @SuppressWarnings("DeprecatedIsStillUsed")
    public SDL2ControllerManager getControllerManager() {
        return controllerManager;
    }

    public List<HaishinThread> getThreads() {
        return gameThreads;
    }

    public HaishinPanel getPanel() {
        return panel;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public ApiService getApiService() {
        return apiService;
    }

    public SchedulerManager getScheduler() {
        return schedulerManager;
    }

    public InputManager getInputManager() {
        return inputManager;
    }

    public boolean isFocus() {
        return frame.hasFocus();
    }
}
