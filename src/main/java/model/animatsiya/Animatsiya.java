package model.animatsiya;

/**
 * Класс для хранения параметров анимации слайда
 */
public class Animatsiya {
    private TipAnimatsii tip;
    private int prodolzhitelnost; // в миллисекундах
    private boolean avtomaticheskiyPerehod;
    private int zaderzhkaPeredPokazom; // задержка перед показом

    public void ustanovitTip(model.animatsiya.TipAnimatsii tip) {
    }

    public enum TipAnimatsii {
        NET,
        PLANKO,
        POYAVLENIE_SLEVA,
        POYAVLENIE_SPRAVA,
        PRIBLLIZHENIE,
        VRAЩENIE
    }

    public Animatsiya() {
        this.tip = TipAnimatsii.PLANKO;
        this.prodolzhitelnost = 1000;
        this.avtomaticheskiyPerehod = false;
        this.zaderzhkaPeredPokazom = 0;
    }

    public Animatsiya(TipAnimatsii tip, int prodolzhitelnost) {
        this.tip = tip;
        this.prodolzhitelnost = prodolzhitelnost;
        this.avtomaticheskiyPerehod = false;
        this.zaderzhkaPeredPokazom = 0;
    }

    // Геттеры и сеттеры
    public static Animatsiya sozdat(TipAnimatsii tip, int prodolzhitelnost) {
        Animatsiya anim = new Animatsiya();
        anim.ustanovitTip(tip);
        anim.ustanovitProdolzhitelnost(prodolzhitelnost);
        return anim;
    }
    public TipAnimatsii poluchitTip() { return tip; }
    public void ustanovitTip(TipAnimatsii tip) { this.tip = tip; }

    public int poluchitProdolzhitelnost() { return prodolzhitelnost; }
    public void ustanovitProdolzhitelnost(int prodolzhitelnost) {
        this.prodolzhitelnost = Math.max(100, Math.min(10000, prodolzhitelnost));
    }

    public boolean isAvtomaticheskiyPerehod() { return avtomaticheskiyPerehod; }
    public void ustanovitAvtomaticheskiyPerehod(boolean avtomaticheskiyPerehod) {
        this.avtomaticheskiyPerehod = avtomaticheskiyPerehod;
    }

    public int poluchitZaderzhkuPeredPokazom() { return zaderzhkaPeredPokazom; }
    public void ustanovitZaderzhkuPeredPokazom(int zaderzhkaPeredPokazom) {
        this.zaderzhkaPeredPokazom = Math.max(0, zaderzhkaPeredPokazom);
    }
}