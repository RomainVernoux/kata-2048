package fr.vernoux.lab.board;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    public boolean isATile() {
        return !isEmpty();
    }

    public void setTile(int value) {
        this.value = value;
    }

    public Cell merge(Cell cell) {
        return new Cell(asInt() * 2);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        return new EqualsBuilder()
                .append(value, cell.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }
}
