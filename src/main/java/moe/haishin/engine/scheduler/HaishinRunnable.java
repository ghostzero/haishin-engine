package moe.haishin.engine.scheduler;

import moe.haishin.engine.Haishin;
import moe.haishin.engine.scene.Scene;

public abstract class HaishinRunnable implements Runnable {
    private long taskId = -1;

    /**
     * Attempts to cancel this task.
     *
     * @throws IllegalStateException if task was not scheduled yet
     */
    public synchronized void cancel() throws IllegalStateException {
        Haishin.getScheduler().cancel(getTaskId());
    }

    private long getTaskId() {
        return taskId;
    }

    public HaishinTask runTaskAsynchronously(Scene scene, long period) {
        checkState();
        return setupId(Haishin.getScheduler().runTaskAsynchronously(scene, this, period));
    }

    public HaishinTask runTaskAsynchronously(Scene scene, long delay, long period) {
        checkState();
        return setupId(Haishin.getScheduler().runTaskAsynchronously(scene, this, delay, period));
    }

    private void checkState() {
        if (taskId != -1) {
            throw new IllegalStateException("Already scheduled as " + taskId);
        }
    }

    private HaishinTask setupId(HaishinTask haishinTask) {
        this.taskId = haishinTask.getTaskId();

        return haishinTask;
    }
}
