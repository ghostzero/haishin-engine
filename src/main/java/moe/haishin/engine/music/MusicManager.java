package moe.haishin.engine.music;

import lombok.SneakyThrows;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MusicManager {
    protected Map<String, URL> sounds = new HashMap<>();
    protected final List<Audio> loops = new ArrayList<>();
    private float volume = 0.7f;
    private boolean keepLoops = false;

    public MusicManager() {
        // do nothing
    }

    @SneakyThrows
    public MusicManager register(String resource, String path) {
        // ensure that the path contains / at the beginning
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        System.out.println("Registered music: " + resource + " from " + path);
        sounds.put(resource, getClass().getResource(path));

        return this;
    }

    public Audio loopWithinScene(String reference) {
        return play(reference, Audio.LOOP_CONTINUOUSLY);
    }

    public Audio play(String reference) {
        return play(reference, Audio.LOOP_DISABLE);
    }

    public Audio play(String reference, int loop) {
        if (!sounds.containsKey(reference)) {
            register(reference, String.format("sounds/%s.wav", reference));
        }

        try {
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(sounds.get(reference));
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);

            Audio audio = new Audio(this, clip, loop);

            if (loop == Audio.LOOP_CONTINUOUSLY) {
                loops.add(audio);
            }

            audio.start();

            return audio;
        } catch (Exception e) {
            e.printStackTrace();
            //whatevers
        }

        return null;
    }

    public boolean isVolumeOn() {
        return volume > 0;
    }

    public void keepAllLoopsForNextScene() {
        this.keepLoops = true;
    }

    public void stopAllLoops() {
        if (keepLoops) {
            keepLoops = false;
            return;
        }
        for (Audio loop : loops) {
            loop.stop();
        }
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float v) {
        volume = Math.min(1.0f, Math.max(0.0f, v));
        for (Audio loop : loops) {
            loop.setVolume(getVolume());
        }
    }

    public void play(VoiceLine voiceLine) {
        play(voiceLine.getResource(), Audio.LOOP_DISABLE);
    }
}
