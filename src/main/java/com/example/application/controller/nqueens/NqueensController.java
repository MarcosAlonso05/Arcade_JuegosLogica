package com.example.application.controller.nqueens;

import com.example.application.model.boards.QueenBoard;
import com.example.application.model.factory.Piece.Piece_Factory;
import com.example.application.model.factory.Piece.QueenPieceFactory;
import com.example.application.model.factory.boards.Board_Factory;
import com.example.application.model.factory.boards.QueenBoardFactory;
import com.example.application.model.logic.BacktrackingSolver;
import com.example.application.model.pieces.Piece;
import com.example.application.controller.TimerController;


public class NqueensController {
    private QueenBoard board;
    private int queensPlaced = 0;
    private BacktrackingSolver solver;
    private boolean firstMove = true;
    private final Piece_Factory pieceFactory;
    private final TimerController timerController = new TimerController();

    public NqueensController(int size) {
        Board_Factory boardFactory = new QueenBoardFactory(size);
        this.board = (QueenBoard) boardFactory.createBoard();
        this.pieceFactory = new QueenPieceFactory();
    }

    public boolean placeQueen(int x, int y) {
        Piece queen = pieceFactory.createPiece();
        boolean placed = board.placePiece(x, y, queen);
        if (placed) {
            queensPlaced++;
            if (firstMove) {
                solver = new BacktrackingSolver(board.getSize(), x, y);
                firstMove = false;
                timerController.startTimer();
            }
            if (hasWon()) {
                timerController.stopTimer();
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
        timerController.resetTimer();
    }

    public QueenBoard getBoard() {
        return board;
    }

    public String getFormattedTime() {
        return timerController.getFormattedTime();
    }

    public long getElapsedTimeMillis() {
        return timerController.getElapsedTimeMillis();
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
