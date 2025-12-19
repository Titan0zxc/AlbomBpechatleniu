package model.Slaid;

import model.kontent.Kontent;
import model.animatsiya.Animatsiya;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Конкретная реализация слайда с изображением
 */
public class IzobrazhenieSlaid extends Slaid {
    private BufferedImage buferIzobrazheniya;

    public IzobrazhenieSlaid() {
        this.id = UUID.randomUUID().toString();
        this.spisokKontenta = new ArrayList<>();
        this.animatsiya = new Animatsiya();
    }

    @Override
    public void otobrazhit() {
        // Создаем буфер для отрисовки
        if (osnovnoeIzobrazhenie != null) {
            int shirina = osnovnoeIzobrazhenie.getWidth(null);
            int visota = osnovnoeIzobrazhenie.getHeight(null);

            buferIzobrazheniya = new BufferedImage(shirina, visota, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buferIzobrazheniya.createGraphics();

            // Рисуем основное изображение
            g2d.drawImage(osnovnoeIzobrazhenie, 0, 0, null);

            // Рисуем весь контент поверх изображения
            for (Kontent kontent : spisokKontenta) {
                Rectangle granitsi = new Rectangle(0, 0, shirina, visota);
                kontent.risovat(g2d, granitsi);
            }

            g2d.dispose();
        }
    }

    @Override
    public void dobavitKontent(Kontent kontent) {
        if (kontent != null && !spisokKontenta.contains(kontent)) {
            spisokKontenta.add(kontent);
        }
    }

    @Override
    public void udalitKontent(Kontent kontent) {
        spisokKontenta.remove(kontent);
    }

    public BufferedImage poluchitBuferIzobrazheniya() {
        return buferIzobrazheniya;
    }

    public List<Kontent> poluchitSpisokKontenta() {
        return new ArrayList<>(spisokKontenta);
    }
}