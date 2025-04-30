package com.example.application.controller.horse;

import com.example.application.model.boards.HorseBoard;
import com.example.application.model.factory.Piece.HorsePieceFactory;
import com.example.application.model.factory.boards.HorseBoardFactory;
import com.example.application.model.logic.KnightTourLogic;

public class HorseController {
    private HorseBoard board;
    private KnightTourLogic logic;
    private int size;

    public HorseController(int size) {
        this.size = size;
        this.logic = new KnightTourLogic();
        initializeBoard();
    }

    private void initializeBoard() {
        HorseBoardFactory boardFactory = new HorseBoardFactory(size);
        this.board = (HorseBoard) boardFactory.createBoard();
    }

    public boolean placeHorse(int x, int y) {
        HorsePieceFactory pieceFactory = new HorsePieceFactory(x, y);
        return board.placePiece(x, y, pieceFactory.createPiece());
    }

    public boolean moveHorse(int toX, int toY) {
        if (board.getHorse() == null) {
            return placeHorse(toX, toY);
        }

        int fromX = board.getHorse().getX();
        int fromY = board.getHorse().getY();

        if (!isValidMove(fromX, fromY, toX, toY)) {
            return false;
        }

        if (board.getBoard()[toX][toY] != 0) {
            return false;
        }

        board.getBoard()[toX][toY] = board.getLastMoveNumber() + 1;
        board.getHorse().setPosition(toX, toY);
        return true;
    }

    public boolean solve() {
        if (board.getHorse() == null) return false;

        int[][] originalBoard = new int[board.getSize()][board.getSize()];
        for (int i = 0; i < board.getSize(); i++) {
            System.arraycopy(board.getBoard()[i], 0, originalBoard[i], 0, board.getSize());
        }

        board.clearExceptStart();

        boolean result = logic.solve(board);

        if (!result) {
            for (int i = 0; i < board.getSize(); i++) {
                System.arraycopy(originalBoard[i], 0, board.getBoard()[i], 0, board.getSize());
            }
        }

        return result;
    }

    public void resetBoard() {
        board.reset();
    }

    public HorseBoard getBoard() {
        return board;
    }

    public boolean isValidMove(int fromX, int fromY, int toX, int toY) {
        int xDiff = Math.abs(toX - fromX);
        int yDiff = Math.abs(toY - fromY);
        return (xDiff == 2 && yDiff == 1) || (xDiff == 1 && yDiff == 2);
    }

    public boolean hasWon() {
        return board.getLastMoveNumber() == size * size;
    }

    public boolean hasLost() {
        if (board.getHorse() == null) return false;

        int x = board.getHorse().getX();
        int y = board.getHorse().getY();

        // Verificar si hay movimientos posibles
        for (int i = 0; i < 8; i++) {
            int nextX = x + KnightTourLogic.getXMoves()[i];
            int nextY = y + KnightTourLogic.getYMoves()[i];

            if (nextX >= 0 && nextX < size && nextY >= 0 && nextY < size &&
                    board.getBoard()[nextX][nextY] == 0) {
                return false;
            }
        }
        return !hasWon();
    }


}