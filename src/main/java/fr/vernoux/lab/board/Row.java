package fr.vernoux.lab.board;

public class Row extends CellLine {

    public Row() {
    }

    public Row(int[] values) {
        super(values);
    }

    public void moveTilesLeft() {
        this.moveTilesToStart();
    }

    public void moveTilesRight() {
        this.moveTilesToEnd();
    }
}
