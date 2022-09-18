package moe.haishin.engine.image;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Loads images from the resources folder into a map.
 */
public class ImageManager {
    public Map<String, BufferedImage> images = new HashMap<>();

    @SneakyThrows

    public ImageManager register(String reference, String path) {
        // ensure that the path contains / at the beginning
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        images.put(reference, bufferedImage);

        return this;
    }

    public BufferedImage get(String reference) {
        return images.get(reference);
    }
}
