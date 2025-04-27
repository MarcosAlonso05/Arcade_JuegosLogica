package com.example.application.model.boards;

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
            marckPossibilitys(x, y);
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

    private void marckPossibilitys(int x, int y) {
        for (int i = 0; i < this.size; i++) {
            if (this.possibilitys[i][y]) {
                this.possibilitys[i][y] = false;
            }
        }

        for (int i = 0; i < this.size; i++) {
            if (this.possibilitys[x][i]) {
                this.possibilitys[x][i] = false;
            }
        }

        //diagonals

        // Up left
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            if (this.possibilitys[i][j]) {
                this.possibilitys[i][j] = false;
            }
        }

        // Up right
        for (int i = x - 1, j = y + 1; i >= 0 && j < this.size; i--, j++) {
            if (this.possibilitys[i][j]) {
                this.possibilitys[i][j] = false;
            }
        }

        // Down left
        for (int i = x + 1, j = y - 1; i < this.size && j >= 0; i++, j--) {
            if (this.possibilitys[i][j]) {
                this.possibilitys[i][j] = false;
            }
        }

        // Down right
        for (int i = x + 1, j = y + 1; i < this.size && j < this.size; i++, j++) {
            if (this.possibilitys[i][j]) {
                this.possibilitys[i][j] = false;
            }
        }

    }

    public boolean getPossibilitys(int x, int y) {
        return this.possibilitys[x][y];
    }

    public Piece getPiece(int x, int y) {
        return this.board[x][y];
    }
}
