package com.example.application.model.pieces;

public class HorsePiece implements Piece {
    private int x;
    private int y;

    public HorsePiece(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void move() {
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}