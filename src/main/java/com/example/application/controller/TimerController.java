package com.example.application.controller;

import com.example.application.model.logic.Timer;

public class TimerController {
    private final Timer timer;

    public TimerController() {
        this.timer = new Timer();
    }

    public void startTimer() {
        timer.start();
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
