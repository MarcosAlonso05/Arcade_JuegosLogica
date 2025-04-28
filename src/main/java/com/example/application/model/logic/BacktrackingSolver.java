package com.example.application.model.logic;

public class BacktrackingSolver {
    private int size;
    private boolean[][] solutionBoard;
    private boolean[][] possibilitys;

    public BacktrackingSolver(int size, int startRow, int startCol) {
        this.size = size;
        solutionBoard = new boolean[size][size];
        this.possibilitys = new boolean[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                solutionBoard[i][j] = false;
            }
        }

        solutionBoard[startRow][startCol] = true;
        this.possibilitys = MarckPossibilitysQueen.marck(this.possibilitys, startRow, startCol);

        solve(1);
    }

    public boolean[][] getSolutionBoard() {
        return solutionBoard;
    }

    public boolean solve(int queensPlaced){
        if (queensPlaced == size) {
            return true;
        }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (possibilitys[row][col] && !solutionBoard[row][col]) {
                    boolean[][] previousPossibilitys = cloneBoard(possibilitys);

                    solutionBoard[row][col] = true;
                    possibilitys = MarckPossibilitysQueen.marck(possibilitys, row, col);

                    if (solve(queensPlaced + 1)) {
                        return true;
                    }

                    solutionBoard[row][col] = false;
                    possibilitys = previousPossibilitys;
                }
            }
        }
        return false;
    }

    private boolean[][] cloneBoard(boolean[][] original) {
        boolean[][] copy = new boolean[size][size];
        for (int i = 0; i < size; i++) {
            System.arraycopy(original[i], 0, copy[i], 0, size);
        }
        return copy;
    }


}
