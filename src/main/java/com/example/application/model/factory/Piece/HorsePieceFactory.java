package com.example.application.model.factory.Piece;

import com.example.application.model.pieces.HorsePiece;
import com.example.application.model.pieces.Piece;

public class HorsePieceFactory implements Piece_Factory {
    private int x;
    private int y;

    public HorsePieceFactory(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Piece createPiece() {
        return new HorsePiece(x, y);
    }
}