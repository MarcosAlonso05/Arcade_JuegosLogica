package com.example.application.model.boards;

import com.example.application.model.pieces.Piece;

public interface Board {
    void reset();
    boolean placePiece(int x, int y, Piece piece);
    boolean removePiece(int x, int y);
}