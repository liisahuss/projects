package se.kth.liisabella.labb4.model;

import java.io.Serializable;

public class SudokuCell implements Serializable {
    private int correctValue;
    private int currentValue;
    private boolean cellIsFixed;

    public SudokuCell(int correctValue, boolean cellIsFixed){
        this.correctValue = correctValue;
        this.cellIsFixed = cellIsFixed;
        if (cellIsFixed) {
            this.currentValue = correctValue;
        } else {
            this.currentValue=0;
        }
    }

    public int getCorrectValue() {
        return correctValue;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public boolean isFixed() {
        return cellIsFixed;
    }

    public boolean numberIsCorrect(){
        return currentValue == correctValue;
    }

    public boolean isEmpty(){
        return currentValue == 0;
    }

    public void clear(){
        if(!cellIsFixed){
            currentValue=0;
        }
    }

    public void setValue(int value){
        if(!cellIsFixed && value >= 1 && value <= 9){
            currentValue = value;
        }
    }
}
