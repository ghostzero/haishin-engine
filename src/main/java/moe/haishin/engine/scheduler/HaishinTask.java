package moe.haishin.engine.scheduler;

import moe.haishin.engine.Haishin;
import moe.haishin.engine.scene.Scene;

import java.util.concurrent.ConcurrentHashMap;

public class HaishinTask implements Runnable {
    public static final int ERROR = 0;
    public static final int NO_REPEATING = -1;
    public static final int CANCEL = -2;
    public static final int PROCESS_FOR_FUTURE = -3;
    public static final int DONE_FOR_FUTURE = -4;

    private final ConcurrentHashMap<Long, HaishinTask> runners;
    private final Scene scene;
    private final Runnable runnable;
    private final long taskId;
    private final long period;
    private long nextRun;

    public HaishinTask(ConcurrentHashMap<Long, HaishinTask> runners, Scene scene, Runnable runnable, final long taskId, final long period) {
        this.runners = runners;
        this.scene = scene;
        this.runnable = runnable;
        this.taskId = taskId;
        this.period = period;
    }

    public final long getTaskId() {
        return taskId;
    }

    public long getPeriod() {
        return period;
    }

    public void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }

    public long getNextRun() {
        return nextRun;
    }

    public void cancel() {
        Haishin.getScheduler().cancel(taskId);
    }

    @Override
    public void run() {
        runnable.run();
    }
}
