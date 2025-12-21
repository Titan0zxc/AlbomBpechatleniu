package servisi;

import model.animatsiya.Animatsiya;
import model.Slaid.Slaid;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.*;

public class AnimationService {

    private ScheduledExecutorService executor;

    public AnimationService() {
        this.executor = Executors.newSingleThreadScheduledExecutor();
    }

    public void vipolnitAnimatsiyu(Slaid slaid, Component komponent, Animatsiya animatsiya) {
        if (animatsiya == null || komponent == null || komponent.getParent() == null) {
            return;
        }

        // Простая анимация - плавное появление
        komponent.setVisible(false);

        int shagov = 20;
        int zaderzhka = animatsiya.poluchitProdolzhitelnost() / shagov;

        executor.scheduleAtFixedRate(new Runnable() {
            float prozrachnost = 0.0f;
            int shag = 0;

            @Override
            public void run() {
                if (shag >= shagov) {
                    komponent.setVisible(true);
                    executor.shutdown();
                    executor = Executors.newSingleThreadScheduledExecutor();
                    return;
                }

                prozrachnost = (float) shag / shagov;
                komponent.setVisible(true);
                komponent.repaint();
                shag++;
            }
        }, 0, zaderzhka, TimeUnit.MILLISECONDS);
    }

    public void ostanovitAnimatsiyu() {
        if (executor != null && !executor.isShutdown()) {
            executor.shutdownNow();
            executor = Executors.newSingleThreadScheduledExecutor();
        }
    }
}