package com.example.application.model.logic;

public class Timer {
    private long startTime;
    private long endTime;
    private boolean running;

    public void start() {
        startTime = System.currentTimeMillis();
        running = true;
    }

    public void stop() {
        endTime = System.currentTimeMillis();
        running = false;
    }

    public long getElapsedTimeMillis() {
        return running ? System.currentTimeMillis() - startTime : endTime - startTime;
    }

    public String getFormattedTime() {
        long millis = getElapsedTimeMillis();
        long seconds = (millis / 1000) % 60;
        long minutes = (millis / 1000) / 60;
        return String.format("%02d:%02d", minutes, seconds);
    }

    public boolean isRunning() {
        return running;
    }

    public void reset() {
        startTime = 0;
        endTime = 0;
        running = false;
    }
}
