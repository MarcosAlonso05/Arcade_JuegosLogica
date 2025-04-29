package com.example.application.model.factory.boards;


import com.example.application.model.boards.Board;
import com.example.application.model.boards.QueenBoard;

public class QueenBoardFactory implements Board_Factory {
    private int size;

    public QueenBoardFactory(int size) {
        this.size = size;
    }

    @Override
    public Board createBoard() {
        return new QueenBoard(size);
    }
}
