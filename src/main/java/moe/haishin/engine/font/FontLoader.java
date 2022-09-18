package moe.haishin.engine.font;

import moe.haishin.engine.HaishinCanvas;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FontLoader {
    public static Font register(String path) throws IOException, FontFormatException {
        // ensure that the path contains / at the beginning
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        // create file from resource path
        InputStream fontVisitorInputStream = HaishinCanvas.class.getResourceAsStream(path);
        return Font.createFont(Font.TRUETYPE_FONT, Objects.requireNonNull(fontVisitorInputStream));
    }
}
