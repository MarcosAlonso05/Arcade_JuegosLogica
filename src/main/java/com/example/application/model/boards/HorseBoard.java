package com.example.application.model.boards;

import com.example.application.model.pieces.Piece;

public class HorseBoard implements Board {
    @Override
    public void reset() {

    }

    @Override
    public boolean placePiece(int x, int y, Piece piece) {
        return false;
    }

    @Override
    public boolean removePiece(int x, int y) {
        return false;
    }
}
