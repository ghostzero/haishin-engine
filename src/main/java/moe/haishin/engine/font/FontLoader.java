package moe.haishin.engine.font;

import moe.haishin.engine.Asset;
import moe.haishin.engine.HaishinCanvas;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class FontLoader {
    public static Font register(String path) throws IOException, FontFormatException {
        return Font.createFont(Font.TRUETYPE_FONT, Asset.get(path));
    }
}
