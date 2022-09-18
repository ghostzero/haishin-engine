package moe.haishin.engine.music;

public class VoiceLine {
    private final String sfx;
    private final String name;

    public VoiceLine(String sfx, String name) {
        this.sfx = sfx;
        this.name = name;
    }

    public String getResource() {
        return sfx;
    }

    public String getName() {
        return name;
    }
}
