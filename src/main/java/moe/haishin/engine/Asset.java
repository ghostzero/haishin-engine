package moe.haishin.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Asset {
    private static File baseDirectory;
    private static final Logger log = LoggerFactory.getLogger(Asset.class);

    public static void setBaseDirectory(File path) {
        baseDirectory = path;
    }

    public static File getBaseDirectory() {
        return baseDirectory;
    }

    public static File get(String path) {
        File file = new File(baseDirectory, sanitise(path));
        log.debug(String.format("Requesting file from %s", file.getAbsolutePath()));
        return file;
    }

    private static String sanitise(String path) {
        // ensure that the path contains / at the beginning
        if (path.startsWith("/")) {
            return path.substring(1);
        }

        return path;
    }
}
