package fr.vernoux.lab.board;

public class Cell {
    private int value;

    public Cell() {
        this.value = 0;
    }

    public Cell(int value) {
        this.value = value;
    }

    public int asInt() {
        return value;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    public void setTile(int value) {
        this.value = value;
    }
}
