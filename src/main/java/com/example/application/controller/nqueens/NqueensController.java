package com.example.application.controller.nqueens;

import com.example.application.model.boards.QueenBoard;
import com.example.application.model.logic.BacktrackingSolver;
import com.example.application.model.pieces.QueenPiece;

public class NqueensController {
    private QueenBoard board;
    private int queensPlaced = 0;
    private BacktrackingSolver solver;
    private boolean firstMove = true;

    public NqueensController(int size) {
        board = new QueenBoard(size);
    }

    public boolean placeQueen(int x, int y) {
        boolean placed = board.placePiece(x, y, new QueenPiece());
        if (placed) {
            queensPlaced++;
            if (firstMove) {
                solver = new BacktrackingSolver(board.getSize(), x, y);
                firstMove = false;
            }
        }
        return placed;
    }

    public boolean removePiece(int x, int y) {
        boolean removed = board.removePiece(x, y);
        if (removed) {
            queensPlaced--;
        }
        return removed;
    }

    public void resetBoard() {
        board.reset();
        queensPlaced = 0;
        solver = null;
        firstMove = true;
    }

    public QueenBoard getBoard() {
        return board;
    }

    public boolean hasWon() {
        return queensPlaced == board.getSize();
    }

    public boolean hasLost() {
        return !hasAnyPossibilityLeft();
    }

    private boolean hasAnyPossibilityLeft() {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                if (board.getPossibilitys(i, j) && board.getPiece(i, j) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean[][] getSolutionBoard() {
        if (solver != null) {
            return solver.getSolutionBoard();
        }
        return null;
    }
}
