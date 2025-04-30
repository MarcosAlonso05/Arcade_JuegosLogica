package com.example.application.model.factory.Piece;


import com.example.application.model.pieces.Piece;
import com.example.application.model.pieces.QueenPiece;

public class QueenPieceFactory implements Piece_Factory {
    @Override
    public Piece createPiece() {
        return new QueenPiece();
    }
}