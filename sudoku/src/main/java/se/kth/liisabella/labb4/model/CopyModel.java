package se.kth.liisabella.labb4.model;

import java.io.Serializable;

import static se.kth.liisabella.labb4.model.SudokuUtilities.GRID_SIZE;
import static se.kth.liisabella.labb4.model.SudokuUtilities.SudokuLevel;

public class CopyModel implements Serializable{
    private int[][] solutionBoard;
    private int[][] userBoard;
    private boolean[][] editable;
    private int selectedRow = -1;
    private int selectedCol = -1;
    private SudokuLevel currentLevel;

    public CopyModel() {
        currentLevel = SudokuLevel.EASY;
    }
    public SudokuLevel getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(SudokuLevel level){
        this.currentLevel = level;
    }

    public void setupGame(SudokuLevel level){
        int[][][] boards = SudokuUtilities.generateSudokuMatrix(level);
        this.currentLevel = level;
        solutionBoard = new int [GRID_SIZE][GRID_SIZE];
        userBoard = new int[GRID_SIZE][GRID_SIZE];
        editable = new boolean[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++){
            for(int col = 0; col < GRID_SIZE; col++){
                solutionBoard[row][col] = boards[row][col][1];
                userBoard[row][col] = boards[row][col][0];
                editable[row][col] = boards[row][col][0]==0;
            }
        }
    }
    public void setSelectedRow(int row){
        this.selectedRow = row;
    }
    public void setSelectedCol(int col){
        this.selectedCol = col;
    }

    public int getSelectedRow(){
        return this.selectedRow;
    }
    public int getSelectedCol(){
        return this.selectedCol;
    }

    public int getCell(int row, int col){
        return userBoard[row][col];
    }
    public int getSolution(int row, int col){
        return solutionBoard[row][col];
    }
    public boolean isCellEditable(int row, int col){
        return editable[row][col];
    }

    public void setCell(int row, int col, int value){
        if(isCellEditable(row, col)){
            userBoard[row][col] = value;
        }
    }
    public void clearCell(int row, int col){
        if(isCellEditable(row, col)){
            userBoard[row][col]=0;
        }
    }
    public void clearAllUserCells(){
        for(int row=0; row < GRID_SIZE; row++){
            for(int col=0; col < GRID_SIZE; col++){
                if(editable[row][col]){
                    userBoard[row][col]=0;
                }
            }
        }
    }
}
