package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Board {

    private List<List<Cell>> rows;

    private Board() {
    }

    public void moveLeft() {
        rows.forEach(this::moveTilesLeft);
    }

    public int[][] getContent() {
        return new int[][]{
                this.rows.get(0).stream().mapToInt(Cell::asInt).toArray(),
                this.rows.get(1).stream().mapToInt(Cell::asInt).toArray(),
                this.rows.get(2).stream().mapToInt(Cell::asInt).toArray(),
                this.rows.get(3).stream().mapToInt(Cell::asInt).toArray(),
        };
    }

    private void moveTilesLeft(List<Cell> row) {
        for (int cellIndex = 0; cellIndex < 4; cellIndex++) {
            moveTileLeft(row, cellIndex);
        }
    }

    private void moveTileLeft(List<Cell> row, int cellIndex) {
        Cell cell = row.get(cellIndex);
        Optional<Cell> targetCellOption = firstEmptyCell(row);
        targetCellOption.ifPresent(targetCell -> {
            targetCellOption.get().setTile(cell.asInt());
            cell.setTile(0);
        });
    }

    private Optional<Cell> firstEmptyCell(List<Cell> row) {
        return row.stream()
                .filter(Cell::isEmpty)
                .findFirst();
    }

    private void addRandomTile(RandomGenerator randomGenerator) {
        List<Cell> emptyCells = emptyCells();
        int randomCellIndex = randomGenerator.randomInt(0, emptyCells.size());
        emptyCells.get(randomCellIndex).setTile(2);
    }

    private List<Cell> emptyCells() {
        return this.rows.stream()
                .flatMap(Collection::stream)
                .filter(Cell::isEmpty)
                .collect(toList());
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
        board.rows = rowsFromContent(content);
        return board;
    }

    private static List<List<Cell>> emptyRows() {
        return Stream.generate(Board::emptyRow).limit(4).collect(toList());
    }

    private static List<Cell> emptyRow() {
        return Stream.generate(Cell::new).limit(4).collect(toList());
    }

    private static List<List<Cell>> rowsFromContent(int[][] content) {
        return Arrays.stream(content)
                .map(Board::rowFromContent)
                .collect(toList());
    }

    private static List<Cell> rowFromContent(int[] rowContent) {
        return Arrays.stream(rowContent)
                .mapToObj(Cell::new)
                .collect(toList());
    }
}
