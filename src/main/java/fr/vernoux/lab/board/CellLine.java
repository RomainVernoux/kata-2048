package fr.vernoux.lab.board;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class CellLine {

    private final List<Cell> cells;

    public CellLine() {
        this.cells = Stream.generate(Cell::new).limit(4).collect(toList());
    }

    public CellLine(int[] values) {
        this.cells = Arrays.stream(values)
                .mapToObj(Cell::new)
                .collect(toList());
    }

    public void moveTilesToStart() {
        for (int cellIndex = 0; cellIndex < 4; cellIndex++) {
            moveTileToStart(cellIndex);
        }
    }

    public void moveTilesToEnd() {
        for (int cellIndex = 3; cellIndex >= 0; cellIndex--) {
            moveTileToEnd(cellIndex);
        }
    }

    public int[] getValues() {
        return cells.stream().mapToInt(Cell::asInt).toArray();
    }

    public List<Cell> getCells() {
        return cells;
    }

    private void moveTileToStart(int cellIndex) {
        Cell cell = this.cells.get(cellIndex);
        Optional<Cell> targetCellOption = firstEmptyCell();
        targetCellOption.ifPresent(targetCell -> {
            targetCellOption.get().setTile(cell.asInt());
            cell.setTile(0);
        });
    }

    private void moveTileToEnd(int cellIndex) {
        Cell cell = this.cells.get(cellIndex);
        Optional<Cell> targetCellOption = lastEmptyCell();
        targetCellOption.ifPresent(targetCell -> {
            targetCellOption.get().setTile(cell.asInt());
            cell.setTile(0);
        });
    }

    private Optional<Cell> firstEmptyCell() {
        List<Cell> emptyCells = this.cells.stream()
                .filter(Cell::isEmpty).toList();
        return emptyCells.size() == 0 ?
                Optional.empty() :
                Optional.of(emptyCells.get(0));
    }

    private Optional<Cell> lastEmptyCell() {
        List<Cell> emptyCells = this.cells.stream()
                .filter(Cell::isEmpty).toList();
        return emptyCells.size() == 0 ?
                Optional.empty() :
                Optional.of(emptyCells.get(emptyCells.size() - 1));
    }
}
