package moe.haishin.engine.image;

import lombok.SneakyThrows;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
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
        register(reference, path, false);

        return this;
    }

    @SneakyThrows
    public ImageManager register(String reference, String path, Boolean includeFlipped) {
        // ensure that the path contains / at the beginning
        if (!path.startsWith("/")) {
            path = "/" + path;
        }

        BufferedImage bufferedImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(path)));
        images.put(reference, bufferedImage);

        if (includeFlipped) {
            images.put(String.format("%s-flipped", reference), getFlippedImage(bufferedImage));
        }

        return this;
    }

    public static BufferedImage getFlippedImage(BufferedImage bi) {
        BufferedImage flipped = new BufferedImage(
                bi.getWidth(),
                bi.getHeight(),
                bi.getType());
        AffineTransform tran = AffineTransform.getTranslateInstance(bi.getWidth(), 0);
        AffineTransform flip = AffineTransform.getScaleInstance(-1d, 1d);
        tran.concatenate(flip);

        Graphics2D g = flipped.createGraphics();
        g.setTransform(tran);
        g.drawImage(bi, 0, 0, null);
        g.dispose();

        return flipped;
    }

    public BufferedImage get(String reference) {
        return images.get(reference);
    }
}
