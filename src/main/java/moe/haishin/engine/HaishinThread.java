package moe.haishin.engine;

public class HaishinThread extends Thread {
    private boolean running;
    private final Runnable target;
    private final int baseSpeed;
    private long frames = 0;
    private long fpsTimer;
    private long fps;

    public HaishinThread(Runnable target, String name, int baseSpeed) {
        super(name);
        this.target = target;
        this.baseSpeed = baseSpeed;
        this.running = true;
    }

    @Override
    public void run() {
        while (running) {
            target.run();
            calculateFps();
            try {
                Thread.sleep(1000 / baseSpeed);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public final void calculateFps() {
        frames++;
        if (System.currentTimeMillis() - fpsTimer < 1000L) return;
        fpsTimer = System.currentTimeMillis();
        fps = frames;
        frames = 0L;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getFps() {
        return fps;
    }
}
