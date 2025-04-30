package com.example.application.model.factory.boards;

import com.example.application.model.boards.Board;
import com.example.application.model.boards.HorseBoard;

public class HorseBoardFactory implements Board_Factory {
    private int size;

    public HorseBoardFactory(int size) {
        this.size = size;
    }

    @Override
    public Board createBoard() {
        return new HorseBoard(size);
    }
}