package com.example.application.model.factory.Piece;

import com.example.application.model.pieces.HanoiPiece;
import com.example.application.model.pieces.Piece;

public class HanoiPieceFactory implements Piece_Factory {
    private int size;

    public HanoiPieceFactory(int size) {
        this.size = size;
    }

    @Override
    public Piece createPiece() {
        return new HanoiPiece(size);
    }
}