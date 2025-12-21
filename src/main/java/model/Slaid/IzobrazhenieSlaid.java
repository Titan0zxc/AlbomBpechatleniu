package model.Slaid;

import model.Slaid.Slaid;
import model.kontent.Kontent;
import model.animatsiya.Animatsiya;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

public abstract class IzobrazhenieSlaid extends Slaid {
    private BufferedImage buferIzobrazheniya;
    private transient Image originalnoeIzobrazhenie; // transient - не сериализуем
    private Map<String, Object> metaDannie = new HashMap<>();

    public IzobrazhenieSlaid() {
        this.id = UUID.randomUUID().toString();
        this.spisokKontenta = new ArrayList<>();
        this.animatsiya = new Animatsiya();
    }

    @Override
    public void otobrazhit() {
        if (originalnoeIzobrazhenie != null) {
            int shirina = originalnoeIzobrazhenie.getWidth(null);
            int visota = originalnoeIzobrazhenie.getHeight(null);

            buferIzobrazheniya = new BufferedImage(shirina, visota, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = buferIzobrazheniya.createGraphics();

            // Рисуем основное изображение
            g2d.drawImage(originalnoeIzobrazhenie, 0, 0, null);

            // Рисуем контент
            for (Kontent kontent : spisokKontenta) {
                Rectangle granitsi = new Rectangle(0, 0, shirina, visota);
                kontent.risovat(g2d, granitsi);
            }

            g2d.dispose();
        }
    }

    public void ustanovitOriginalnoeIzobrazhenie(Image image) {
        this.originalnoeIzobrazhenie = image;
    }

    public Image getOriginalnoeIzobrazhenie() {
        return originalnoeIzobrazhenie;
    }

    // Для сериализации пути к файлу
    public void ustanovitPutKFailu(String put) {
        metaDannie.put("put_k_failu", put);
    }

    public String poluchitPutKFailu() {
        return (String) metaDannie.get("put_k_failu");
    }

    // Геттер для metaDannie
    public Map<String, Object> getMetaDannie() {
        return metaDannie;
    }
}