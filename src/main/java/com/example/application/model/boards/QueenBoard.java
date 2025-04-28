package com.example.application.model.boards;

import com.example.application.model.logic.MarckPossibilitysQueen;
import com.example.application.model.pieces.Piece;

public class QueenBoard implements Board {

    private int size;
    private Piece[][] board;
    private boolean[][] possibilitys;

    public QueenBoard(int size) {
        this.size = size;
        this.board = new Piece[size][size];
        this.possibilitys = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = null;
                this.possibilitys[i][j] = true;
            }
        }
    }

    @Override
    public void reset() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = null;
                this.possibilitys[i][j] = true;
            }
        }

    }

    @Override
    public boolean placePiece(int x, int y, Piece piece) {
        if (this.board[x][y] == null && this.possibilitys[x][y]) {
            this.board[x][y] = piece;
            this.possibilitys = MarckPossibilitysQueen.marck(this.possibilitys, x, y);
            return true;
        }
        return false;
    }

    @Override
    public boolean removePiece(int x, int y) {
        if (this.board[x][y] != null) {
            this.board[x][y] = null;
            return true;
        }
        return false;
    }

    public boolean getPossibilitys(int x, int y) {
        return this.possibilitys[x][y];
    }

    public Piece getPiece(int x, int y) {
        return this.board[x][y];
    }

    public int getSize() {
        return size;
    }
}
