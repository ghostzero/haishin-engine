package moe.haishin.engine.scheduler;

import moe.haishin.engine.HaishinEngine;
import moe.haishin.engine.scene.Scene;

import java.util.concurrent.ConcurrentHashMap;

public class SchedulerManager {
    private static long ID = 1;
    private final ConcurrentHashMap<Long, HaishinTask> runners = new ConcurrentHashMap<>();
    private long currentTick;

    public SchedulerManager(HaishinEngine haishinEngine) {
    }

    public HaishinTask runTaskAsynchronously(Scene scene, Runnable runnable, long period) {
        return runTaskAsynchronously(scene, runnable, 0, period);
    }

    public HaishinTask runTaskAsynchronously(Scene scene, Runnable runnable, long delay, long period) {
        if (delay < 0L) {
            delay = 0L;
        }

        if (period == HaishinTask.ERROR) {
            period = 1L;
        } else if (period < HaishinTask.NO_REPEATING) {
            period = HaishinTask.NO_REPEATING;
        }

        return handle(new HaishinTask(runners, scene, runnable, nextId(), period), delay);
    }

    private HaishinTask handle(final HaishinTask haishinTask, final long delay) {
        haishinTask.setNextRun(currentTick + delay);
        addTask(haishinTask);
        runners.put(haishinTask.getTaskId(), haishinTask);
        return haishinTask;
    }

    private void addTask(HaishinTask haishinTask) {

    }

    private long nextId() {
        return ID++;
    }

    public void cancel(long id) {
        runners.remove(id);
    }

    public void mainThreadHeartbeat(final long currentTick) {
        this.currentTick = currentTick;
        for (HaishinTask haishinTask : runners.values()) {
            if (haishinTask.getNextRun() <= currentTick) {
                try {
                    haishinTask.run();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                final long period = haishinTask.getPeriod(); // State consistency
                if (period > 0) {
                    haishinTask.setNextRun(currentTick + period);
                } else {
                    runners.remove(haishinTask.getTaskId());
                }
            }
        }
    }

    public final long getCurrentTick() {
        return currentTick;
    }
}
