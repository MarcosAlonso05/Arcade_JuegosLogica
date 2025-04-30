package com.example.application.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "game_records")
public class GameRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gameType;

    private String resultData;

    private long elapsedTime;

    private String formattedTime;

    public GameRecord() {}

    public GameRecord(String gameType, String resultData, long elapsedTime, String formattedTime) {
        this.gameType = gameType;
        this.resultData = resultData;
        this.elapsedTime = elapsedTime;
        this.formattedTime = formattedTime;
    }

    public Long getId() { return id; }
    public String getGameType() { return gameType; }
    public void setGameType(String gameType) { this.gameType = gameType; }

    public String getResultData() { return resultData; }
    public void setResultData(String resultData) { this.resultData = resultData; }

    public long getElapsedTime() { return elapsedTime; }
    public void setElapsedTime(long elapsedTime) { this.elapsedTime = elapsedTime; }

    public String getFormattedTime() { return formattedTime; }
    public void setFormattedTime(String formattedTime) { this.formattedTime = formattedTime; }
}
