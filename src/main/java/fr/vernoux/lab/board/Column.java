package fr.vernoux.lab.board;

public class Column extends CellLine {

    public void moveTilesUp() {
        this.moveTilesToStart();
    }

    public void moveTilesDown() {
        this.moveTilesToEnd();
    }
}
