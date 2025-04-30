package com.example.application.controller;

import com.example.application.model.logic.Timer;

public class TimerController {
    private final Timer timer;

    public TimerController() {
        this.timer = new Timer();
    }

    public void startTimer(Runnable updateUI) {
        timer.start();

        new Thread(() -> {
            while (timer.isRunning()) {
                try {
                    Thread.sleep(1000); // Esperar 1 segundo
                    updateUI.run();  // Actualizar la UI
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public String getFormattedTime() {
        return timer.getFormattedTime();
    }

    public long getElapsedTimeMillis() {
        return timer.getElapsedTimeMillis();
    }

    public void resetTimer() {
        timer.reset();
    }

    public boolean isRunning() {
        return timer.isRunning();
    }
}
