package com.example.application.model.entity;
import jakarta.persistence.*;

@Entity
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int boardSize;
    private long timeMillis;

    public Record() {
    }

    public Record(int boardSize, long timeMillis) {
        this.boardSize = boardSize;
        this.timeMillis = timeMillis;
    }

    public Long getId() {
        return id;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }
}