package com.example.application.controller.hanoi;

import com.example.application.model.boards.HanoiBoard;
import com.example.application.model.factory.Piece.HanoiPieceFactory;
import com.example.application.model.factory.boards.HanoiBoardFactory;
import com.example.application.model.pieces.HanoiPiece;
import com.example.application.model.pieces.Piece;

import java.util.Stack;

public class HanoiController {
    private HanoiBoard board;
    private int selectedTower = -1;
    private int disks;

    public HanoiController(int disks) {
        this.disks = disks;
        initializeBoard();
    }

    private void initializeBoard() {
        HanoiBoardFactory boardFactory = new HanoiBoardFactory(disks);
        this.board = (HanoiBoard) boardFactory.createBoard();
    }

    public boolean selectTower(int tower) {
        if (selectedTower == -1) {
            if (!board.getTower(tower).isEmpty()) {
                selectedTower = tower;
                return true;
            }
            return false;
        } else {
            boolean moved = moveDisk(selectedTower, tower);
            selectedTower = -1;
            return moved;
        }
    }

    private boolean moveDisk(int from, int to) {
        if (from == to) return false;

        Stack<Piece> fromTower = board.getTower(from);
        if (fromTower.isEmpty()) return false;

        HanoiPiece disk = (HanoiPiece) fromTower.peek();
        HanoiPieceFactory pieceFactory = new HanoiPieceFactory(disk.getSize());

        if (board.placePiece(to, 0, pieceFactory.createPiece())) {
            board.removePiece(from, 0);
            return true;
        }
        return false;
    }

    public void resetBoard() {
        board.reset();
        selectedTower = -1;
    }

    public HanoiBoard getBoard() {
        return board;
    }

    public boolean hasWon() {
        return board.isComplete();
    }

    public int getSelectedTower() {
        return selectedTower;
    }

    public int getMoves() {
        return board.getMoves();
    }

    public void solve() {
        resetBoard();
        solveHanoi(disks, 0, 2, 1);
    }

    private void solveHanoi(int n, int from, int to, int aux) {
        if (n == 0) return;
        solveHanoi(n - 1, from, aux, to);
        moveDisk(from, to);
        solveHanoi(n - 1, aux, to, from);
    }
}