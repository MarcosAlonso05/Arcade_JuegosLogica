package com.example.application.model.logic;

public class MarckPossibilitysQueen {

    public static boolean[][] marck(boolean[][] board, int x, int y) {
        int size = board.length;

        for (int i = 0; i < size; i++) {
            board[i][y] = false;
        }

        for (int i = 0; i < size; i++) {
            board[x][i] = false;
        }

        // Up left
        for (int i = x - 1, j = y - 1; i >= 0 && j >= 0; i--, j--) {
            board[i][j] = false;
        }

        // Up right
        for (int i = x - 1, j = y + 1; i >= 0 && j < size; i--, j++) {
            board[i][j] = false;
        }

        // Down left
        for (int i = x + 1, j = y - 1; i < size && j >= 0; i++, j--) {
            board[i][j] = false;
        }

        // Down right
        for (int i = x + 1, j = y + 1; i < size && j < size; i++, j++) {
            board[i][j] = false;
        }

        return board;
    }
}