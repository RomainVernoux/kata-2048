package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Board {

    private List<Row> rows;

    private Board() {
    }

    public void moveLeft() {
        rows.forEach(Row::moveTilesLeft);
    }

    public void moveRight() {
        rows.forEach(Row::moveTilesRight);
    }

    public void moveUp() {
        asColumns().forEach(Column::moveTilesUp);
    }

    public void moveDown() {
        asColumns().forEach(Column::moveTilesDown);
    }

    public int[][] getContent() {
        return this.rows.stream()
                .map(Row::getValues)
                .toArray(int[][]::new);
    }

    private void addRandomTile(RandomGenerator randomGenerator) {
        List<Cell> emptyCells = emptyCells();
        int randomCellIndex = randomGenerator.randomInt(0, emptyCells.size());
        emptyCells.get(randomCellIndex).setTile(2);
    }

    private List<Cell> emptyCells() {
        return this.rows.stream()
                .flatMap(row -> row.getCells().stream())
                .filter(Cell::isEmpty)
                .collect(toList());
    }

    private List<Column> asColumns() {
        List<Column> columns = emptyColumns();
        for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
            for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
                Cell cell = this.rows.get(rowIndex).getCells().get(columnIndex);
                columns.get(columnIndex).getCells().set(rowIndex, cell);
            }
        }
        return columns;
    }

    public static Board newGame(RandomGenerator randomGenerator) {
        Board board = new Board();
        board.rows = emptyRows();
        board.addRandomTile(randomGenerator);
        board.addRandomTile(randomGenerator);
        return board;
    }

    public static Board fromContent(int[][] content) {
        Board board = new Board();
        board.rows = rowsFromValues(content);
        return board;
    }

    private static List<Row> emptyRows() {
        return Stream.generate(Row::new).limit(4).collect(toList());
    }

    private static List<Column> emptyColumns() {
        return Stream.generate(Column::new).limit(4).collect(toList());
    }

    private static List<Row> rowsFromValues(int[][] values) {
        return Arrays.stream(values)
                .map(Row::new)
                .collect(toList());
    }
}
