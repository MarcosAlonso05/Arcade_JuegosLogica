package com.example.application.model.boards;

import com.example.application.model.pieces.HorsePiece;
import com.example.application.model.pieces.Piece;

import java.util.Arrays;

public class HorseBoard implements Board {
    private int size;
    private HorsePiece horse;
    private int[][] board;
    private int lastMoveNumber = 0;

    public HorseBoard(int size) {
        this.size = size;
        this.board = new int[size][size];
        reset();
    }

    @Override
    public void reset() {
        this.horse = null;
        this.lastMoveNumber = 0;
        for (int i = 0; i < size; i++) {
            Arrays.fill(board[i], 0);
        }
    }

    @Override
    public boolean placePiece(int x, int y, Piece piece) {
        if (x < 0 || x >= size || y < 0 || y >= size || board[x][y] != 0) {
            return false;
        }

        if (piece instanceof HorsePiece) {
            this.horse = (HorsePiece) piece;
            this.horse.setPosition(x, y);
            board[x][y] = ++lastMoveNumber;
            return true;
        }
        return false;
    }

    @Override
    public boolean removePiece(int x, int y) {
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return false;
        }
        board[x][y] = 0;
        if (horse != null && horse.getX() == x && horse.getY() == y) {
            horse = null;
        }
        return true;
    }

    public int getSize() {
        return size;
    }

    public int[][] getBoard() {
        return board;
    }

    public HorsePiece getHorse() {
        return horse;
    }

    public int getLastMoveNumber() {
        return lastMoveNumber;
    }

    public void setLastMoveNumber(int lastMoveNumber) {
        this.lastMoveNumber = lastMoveNumber;
    }

    public void setBoard(int[][] newBoard) {
        this.board = newBoard;
    }

    public void clearExceptStart() {
        int startX = horse.getX();
        int startY = horse.getY();
        int startValue = board[startX][startY];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!(i == startX && j == startY)) {
                    board[i][j] = 0;
                }
            }
        }
        board[startX][startY] = startValue;
        lastMoveNumber = 1;
    }
}