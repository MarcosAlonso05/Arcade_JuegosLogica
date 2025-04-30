package com.example.application.model.pieces;

public class HanoiPiece implements Piece {
    private final int size;

    public HanoiPiece(int size) {
        this.size = size;
    }

    @Override
    public void move() {
        // No necesita implementación para Hanoi
    }

    public int getSize() {
        return size;
    }
}