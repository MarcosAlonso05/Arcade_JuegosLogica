package com.example.application.model.factory.boards;

import com.example.application.model.boards.Board;
import com.example.application.model.boards.HanoiBoard;

public class HanoiBoardFactory implements Board_Factory {
    private int disks;

    public HanoiBoardFactory(int disks) {
        this.disks = disks;
    }

    @Override
    public Board createBoard() {
        return new HanoiBoard(disks);
    }
}