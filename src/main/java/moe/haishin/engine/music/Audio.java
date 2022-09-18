package moe.haishin.engine.music;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {

    public static final int LOOP_CONTINUOUSLY = -1;
    public static final int LOOP_DISABLE = 0;
    private final MusicManager musicManager;
    private final Clip clip;
    private final int loop;

    public Audio(MusicManager musicManager, Clip clip, int loop) {
        this.musicManager = musicManager;
        this.clip = clip;
        this.loop = loop;
    }

    public void start() {
        if (musicManager.isVolumeOn()) {
            setVolume(musicManager.getVolume());
            clip.loop(getLoop());
            clip.start();
        }
    }

    private int getLoop() {
        return loop;
    }

    public void stop() {
        clip.stop();
    }

    public boolean isPlaying() {
        return clip.isActive();
    }

    public void setVolume(float volume) {
        FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        float hearingThreshold = volumeControl.getMinimum() + (volumeControl.getMaximum() - volumeControl.getMinimum()) * volume;
        float newVolume = Math.min(volumeControl.getMaximum(), Math.max(volumeControl.getMinimum(), hearingThreshold));
        volumeControl.setValue(newVolume);
    }
}
