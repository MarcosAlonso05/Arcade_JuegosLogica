package com.example.application.model.logic;

import com.example.application.model.boards.HorseBoard;
import com.example.application.model.pieces.HorsePiece;

import java.util.Arrays;

public class KnightTourLogic {
    public static final int[] X_MOVES = {2, 1, -1, -2, -2, -1, 1, 2};
    public static final int[] Y_MOVES = {1, 2, 2, 1, -1, -2, -2, -1};
    private static final int TIMEOUT_MS = 2000; // 2 segundos máximo

    public boolean solve(HorseBoard board) {
        if (board.getHorse() == null) return false;

        int startX = board.getHorse().getX();
        int startY = board.getHorse().getY();
        int size = board.getSize();

        int[][] solution = new int[size][size];
        solution[startX][startY] = 1;

        class Result {
            boolean solved = false;
        }
        Result result = new Result();

        Thread solverThread = new Thread(() -> {
            result.solved = solveUtil(solution, startX, startY, 2, size, System.currentTimeMillis());
        });

        solverThread.start();

        try {
            solverThread.join(TIMEOUT_MS);
            if (solverThread.isAlive()) {
                solverThread.interrupt();
                return false;
            }
        } catch (InterruptedException e) {
            return false;
        }

        if (result.solved) {
            for (int i = 0; i < size; i++) {
                System.arraycopy(solution[i], 0, board.getBoard()[i], 0, size);
            }
            board.setLastMoveNumber(size * size);
        }

        return result.solved;
    }

    private boolean solveUtil(int[][] board, int x, int y, int moveCount, int size, long startTime) {
        if (System.currentTimeMillis() - startTime > TIMEOUT_MS) {
            return false;
        }

        if (moveCount > size * size) {
            return true;
        }

        for (int i = 0; i < 8; i++) {
            int nextX = x + X_MOVES[i];
            int nextY = y + Y_MOVES[i];

            if (isValidMove(board, nextX, nextY, size)) {
                board[nextX][nextY] = moveCount;

                if (solveUtil(board, nextX, nextY, moveCount + 1, size, startTime)) {
                    return true;
                } else {
                    board[nextX][nextY] = 0;
                }
            }
        }
        return false;
    }

    private boolean isValidMove(int[][] board, int x, int y, int size) {
        return x >= 0 && x < size && y >= 0 && y < size && board[x][y] == 0;
    }

    public static int[] getXMoves() {
        return X_MOVES;
    }

    public static int[] getYMoves() {
        return Y_MOVES;
    }
}