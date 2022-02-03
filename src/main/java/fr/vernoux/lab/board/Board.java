package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Board {

    protected List<Cell> content;

    public Board(RandomGenerator randomGenerator) {
        this.content = contentWithEmptyCells();
        int cellIndex1 = randomGenerator.randomInt(0, 16);
        addTileToNthEmptyCell(2, cellIndex1);
        int cellIndex2 = randomGenerator.randomInt(0, 15);
        addTileToNthEmptyCell(2, cellIndex2);
    }

    protected Board() {
    }

    public int[][] getContent() {
        int[][] view = new int[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                view[row][col] = content.get(row * 4 + col).asInt();
            }
        }
        return view;
    }

    private List<Cell> contentWithEmptyCells() {
        return emptyCells().limit(16).collect(toList());
    }

    private void addTileToNthEmptyCell(int tileValue, int cellIndex) {
        this.content.stream()
                .filter(Cell::isEmpty)
                .skip(cellIndex)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not enough empty cells"))
                .setTile(tileValue);
    }

    public void moveLeft() {
        List<List<Cell>> movedContent = rows().stream()
                .map(this::pushToStart)
                .collect(toList());
        this.content = new ArrayList<>();
        movedContent.forEach(row -> this.content.addAll(row));
    }

    private List<Cell> pushToStart(List<Cell> cells) {
        List<Cell> pushedRow = cells.stream()
                .filter(Cell::isATile)
                .collect(toList());
        List<Cell> mergedCells = mergeCells(pushedRow);
        return Stream.concat(mergedCells.stream(), emptyCells()).limit(4).collect(toList());
    }

    private Stream<Cell> emptyCells() {
        return Stream.generate(Cell::new);
    }

    private List<Cell> mergeCells(List<Cell> pushedRow) {
        List<Cell> mergedCells = new ArrayList<>();
        for (int i = 0; i < pushedRow.size(); i++) {
            Cell cell1 = pushedRow.get(i);
            if (i < pushedRow.size() - 1) {
                Cell cell2 = pushedRow.get(i + 1);
                if (cell1.equals(cell2)) {
                    mergedCells.add(cell1.merge(cell2));
                    i++;
                } else {
                    mergedCells.add(cell1);
                }
            } else {
                mergedCells.add(cell1);
            }
        }
        return mergedCells;
    }

    private List<List<Cell>> rows() {
        final AtomicInteger counter = new AtomicInteger();
        return new ArrayList<>(content.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 4))
                .values());
    }
}
