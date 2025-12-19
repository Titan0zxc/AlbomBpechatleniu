package utils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageUtils {

    public static BufferedImage izmenitRazmer(BufferedImage original,
                                              int shirina, int visota) {
        Image temp = original.getScaledInstance(shirina, visota, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(shirina, visota,
                BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    public static BufferedImage sozdatMiniatyru(File fail, int shirina, int visota)
            throws IOException {
        BufferedImage original = ImageIO.read(fail);
        return izmenitRazmer(original, shirina, visota);
    }

    public static boolean podderzhivaetsyaFormat(String imyaFaila) {
        String[] formats = {".jpg", ".jpeg", ".png", ".gif", ".bmp"};
        String lower = imyaFaila.toLowerCase();
        for (String format : formats) {
            if (lower.endsWith(format)) {
                return true;
            }
        }
        return false;
    }
}