package com.example.application.controller.nqueens;

import com.example.application.model.boards.QueenBoard;
import com.example.application.model.pieces.QueenPiece;

public class NqueensController {
    private QueenBoard board;

    public NqueensController(int size) {
        board = new QueenBoard(size);
    }
    public boolean placeQueen(int x, int y) {
        return board.placePiece(x, y, new QueenPiece());
    }

    public boolean removePiece(int x, int y) {
        return board.removePiece(x, y);
    }

    public void resetBoard() {
        board.reset();
    }

    public QueenBoard getBoard() {
        return board;
    }
}