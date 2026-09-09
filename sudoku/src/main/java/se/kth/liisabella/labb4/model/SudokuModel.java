package se.kth.liisabella.labb4.model;

import java.io.Serializable;
import static se.kth.liisabella.labb4.model.SudokuUtilities.*;
import java.util.Random;

public class SudokuModel implements Serializable {
    private SudokuCell[][] board;
    private SudokuLevel currentLevel;
    private int selectedRow = -1;
    private int selectedCol = -1;


    public SudokuModel() {
        currentLevel = SudokuLevel.MEDIUM;
        board = new SudokuCell[GRID_SIZE][GRID_SIZE];
    }

    public void setupGame(SudokuLevel level) {
        int[][][] data = SudokuUtilities.generateSudokuMatrix(level);

        board = new SudokuCell[GRID_SIZE][GRID_SIZE];
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                boolean fixed = data[row][col][0] != 0;
                int correct = data[row][col][1];
                board[row][col] = new SudokuCell(correct, fixed);
                if (!fixed) {
                    board[row][col].clear(); //sätt värde till 0 om inte fixed
                }
            }
        }
    }

    public SudokuLevel getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(SudokuLevel level) {
        this.currentLevel = level;
    }

    public void setSelectedRow(int row) {
        this.selectedRow = row;
    }

    public void setSelectedCol(int col) {
        this.selectedCol = col;
    }

    public int getSelectedRow() {
        return this.selectedRow;
    }

    public int getSelectedCol() {
        return this.selectedCol;
    }

    public int getCellNumber(int row, int col) {
        return board[row][col].getCurrentValue();
    }

    public SudokuCell getCell(int row, int col) {
        return board[row][col];
    }
    public boolean isCellFixed(int row, int col) {
        return board[row][col].isFixed();
    }

    public void setValue(int row, int col, int value) {
        board[row][col].setValue(value);
    }

    public void clearCell(int row, int col) {
        board[row][col].clear();
    }

    public void reset() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                board[row][col].clear();
            }
        }
    }

    public boolean isSolved() {
        for (int r = 0; r < GRID_SIZE; r++) {
            for (int c = 0; c < GRID_SIZE; c++) {
                if (!isCellFixed(r, c)) {
                    SudokuCell cell = getCell(r, c);
                    int val = cell.getCurrentValue();
                    if (val != 0) {
                        if (val != cell.getCorrectValue()) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void getHint() {
        Random rand = new Random();

        // Försök ett rimligt antal gånger att hitta en slumpmässig cell att ge hint till
        for (int i = 0; i < 100; i++) {
            int r = rand.nextInt(GRID_SIZE);
            int c = rand.nextInt(GRID_SIZE);

            if (!isCellFixed(r, c)) {
                SudokuCell cell = getCell(r, c);
                int val = cell.getCurrentValue();
                int solution = cell.getCorrectValue();

                if (val == 0) {
                    cell.setValue(solution);
                    return;
                }
            }
        }
    }

    public boolean isBoardComplete() {
        boolean allCellsAreFilled = true;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (!isCellFixed(row, col)) {
                    SudokuCell cell = getCell(row, col);
                    int userValue = cell.getCurrentValue();
                    if (userValue == 0) {
                        allCellsAreFilled = false;

                    }
                }
            }
        }
        return allCellsAreFilled;
    }

    public boolean isBoardCompleteAndCorrect() {
        boolean allCellsAreCorrect = true;
        if (isBoardComplete() == true) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    SudokuCell cell = getCell(row, col);
                    int userValue = cell.getCurrentValue();
                    int correctValue = cell.getCorrectValue();
                    if (userValue != correctValue) {
                        allCellsAreCorrect = false;
                    }
                }
            }
        }
        return true;
    }
    
}
