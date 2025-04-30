package com.example.application.model.boards;

import com.example.application.model.pieces.HanoiPiece;
import com.example.application.model.pieces.Piece;

import java.util.Stack;

public class HanoiBoard implements Board {
    private int disks;
    private Stack<Piece>[] towers;
    private int moves;

    public HanoiBoard(int disks) {
        this.disks = disks;
        this.towers = new Stack[3];
        for (int i = 0; i < 3; i++) {
            towers[i] = new Stack<>();
        }
        reset();
    }

    @Override
    public void reset() {
        for (Stack<Piece> tower : towers) {
            tower.clear();
        }
        // Llenar la primera torre con discos
        for (int i = disks; i >= 1; i--) {
            towers[0].push(new HanoiPiece(i));
        }
        moves = 0;
    }

    @Override
    public boolean placePiece(int x, int y, Piece piece) {
        if (x < 0 || x >= 3) return false;

        if (towers[x].isEmpty() || ((HanoiPiece)piece).getSize() < ((HanoiPiece)towers[x].peek()).getSize()) {
            towers[x].push(piece);
            moves++;
            return true;
        }
        return false;
    }

    @Override
    public boolean removePiece(int x, int y) {
        if (x < 0 || x >= 3 || towers[x].isEmpty()) return false;
        towers[x].pop();
        return true;
    }

    public Piece peekDisk(int tower) {
        if (tower < 0 || tower >= 3 || towers[tower].isEmpty()) return null;
        return towers[tower].peek();
    }

    public int getDisks() {
        return disks;
    }

    public int getMoves() {
        return moves;
    }

    public boolean isComplete() {
        return towers[2].size() == disks;
    }

    public Stack<Piece> getTower(int index) {
        return towers[index];
    }
}