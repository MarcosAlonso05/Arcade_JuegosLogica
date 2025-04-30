
package com.example.application.model;

public class GameData {
    private String name;
    private String highScore;
    private String numberOfPlayers;
    private String additionalInfo;

    public GameData(String name, String highScore, String numberOfPlayers, String additionalInfo) {
        this.name = name;
        this.highScore = highScore;
        this.numberOfPlayers = numberOfPlayers;
        this.additionalInfo = additionalInfo;
    }

    public String getName() {
        return name;
    }

    public String getHighScore() {
        return highScore;
    }

    public String getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }
}
