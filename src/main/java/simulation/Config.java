package simulation;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private int width = 5;
    private int height = 5;
    private int quantity = 3;
    private int delay = 500;

    public void load(String fileName) throws IOException {
        Properties props = new Properties();
        try (InputStream in = Config.class.getClassLoader().getResourceAsStream(fileName)) {
            if (in == null) throw new IllegalStateException(fileName + " не найден в resources");
            props.load(in);
        }

        width = readInt(props, "width", width, 5, 12);
        height = readInt(props, "height", height, 5, 12);
        delay = readInt(props,"delay", delay, 500, 5000);

        int maxByCells = width * height;
        quantity = readInt(props, "quantity", quantity, 3, maxByCells);
    }

    private static int readInt(Properties props, String key, int def, int min, int max) {
        String raw = props.getProperty(key);
        if (raw == null || raw.isBlank()) {
            return def;
        }

        raw = raw.trim();
        if (raw.endsWith(";")) {
            raw = raw.substring(0, raw.length() - 1).trim();
        }

        int v;
        try {
            v = Integer.parseInt(raw);
        } catch (NumberFormatException e) {
            return def;
        }

        if (v < min) {
            return min;
        }

        if (v > max) {
            return max;
        }
        return v;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getDelay() {
        return delay;
    }
}
