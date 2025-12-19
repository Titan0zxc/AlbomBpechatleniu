package util;

import model.Slaid.IzobrazhenieSlaid;
import model.Slaid.Slaid;
import java.awt.Image;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Утилиты для работы со слайдами
 */
public class UtilSlaid {

    /**
     * Изменяет размер изображения
     */
    public static Image izmenitRazmer(Image originalnoeIzobrazhenie, int shirina, int visota) {
        BufferedImage resizedImage = new BufferedImage(shirina, visota, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = resizedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.drawImage(originalnoeIzobrazhenie, 0, 0, shirina, visota, null);
        g2d.dispose();

        return resizedImage;
    }

    /**
     * Создает миниатюру слайда
     */
    public static BufferedImage sozdatMiniatyru(Slaid slaid, int shirina, int visota) {
        if (slaid instanceof IzobrazhenieSlaid) {
            IzobrazhenieSlaid izobrazhenieSlaid = (IzobrazhenieSlaid) slaid;
            BufferedImage bufer = izobrazhenieSlaid.poluchitBuferIzobrazheniya();

            if (bufer != null) {
                Image resized = izmenitRazmer(bufer, shirina, visota);
                BufferedImage miniatyra = new BufferedImage(shirina, visota, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2d = miniatyra.createGraphics();
                g2d.drawImage(resized, 0, 0, null);
                g2d.dispose();
                return miniatyra;
            }
        }
        return null;
    }
}