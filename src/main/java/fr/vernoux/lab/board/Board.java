package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Board {

    private List<Cell> content;

    public static Board newGame(RandomGenerator randomGenerator) {
        return new Board(randomGenerator);
    }

    public void moveLeft() {
        this.content = rows().stream()
                .map(this::pushToStart)
                .map(this::mergeTiles)
                .map(this::fillWithCells)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public void moveRight() {
        this.content = rows().stream()
                .map(this::reverseRow)
                .map(this::pushToStart)
                .map(this::mergeTiles)
                .map(this::fillWithCells)
                .map(this::reverseRow)
                .flatMap(Collection::stream)
                .collect(toList());
    }

    public void moveUp() {
        List<List<Cell>> movedCells = columns().stream()
                .map(this::pushToStart)
                .map(this::mergeTiles)
                .map(this::fillWithCells)
                .collect(toList());

        this.content = toContent(movedCells);
    }

    public void moveDown() {
        List<List<Cell>> movedCells = columns().stream()
                .map(this::reverseRow)
                .map(this::pushToStart)
                .map(this::mergeTiles)
                .map(this::fillWithCells)
                .map(this::reverseRow)
                .collect(toList());

        this.content = toContent(movedCells);
    }

    private List<Cell> toContent(List<List<Cell>> movedCells) {
        List<Cell> newContent = new ArrayList<>();
        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                newContent.add(movedCells.get(row).get(col));
            }
        }
        return newContent;
    }

    private List<Cell> reverseRow(List<Cell> cells) {
        List<Cell> reversed = new ArrayList<>(cells);
        Collections.reverse(reversed);
        return reversed;
    }

    public static Board fromView(int[][] view) {
        List<Cell> content = Arrays.stream(view)
                .flatMapToInt(Arrays::stream)
                .mapToObj(Cell::new)
                .collect(Collectors.toList());
        return new Board(content);
    }

    private Board(List<Cell> content) {
        this.content = content;
    }

    private Board(RandomGenerator randomGenerator) {
        this.content = contentWithEmptyCells();
        int cellIndex1 = randomGenerator.randomInt(0, 16);
        addTileToNthEmptyCell(2, cellIndex1);
        int cellIndex2 = randomGenerator.randomInt(0, 15);
        addTileToNthEmptyCell(2, cellIndex2);
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

    private List<List<Cell>> columns() {
        List<List<Cell>> columns = new ArrayList<>();
        for (int col = 0; col < 4; col++) {
            List<Cell> column = new ArrayList<>();
            columns.add(column);
            for (int row = 0; row < 4; row++) {
                column.add(content.get(row * 4 + col));
            }
        }
        return columns;
    }

    private List<List<Cell>> rows() {
        final AtomicInteger counter = new AtomicInteger();
        return new ArrayList<>(content.stream()
                .collect(Collectors.groupingBy(it -> counter.getAndIncrement() / 4))
                .values());
    }

    private List<Cell> pushToStart(List<Cell> cells) {
        Stream<Cell> tiles = cells.stream()
                .filter(Cell::isATile);
        return tiles.collect(Collectors.toList());
    }

    private List<Cell> fillWithCells(List<Cell> tiles) {
        return Stream.concat(tiles.stream(), emptyCells()).limit(4).collect(toList());
    }

    private Stream<Cell> emptyCells() {
        return Stream.generate(Cell::new);
    }

    private List<Cell> mergeTiles(List<Cell> tiles) {
        List<Cell> mergedTiles = new ArrayList<>();
        for (int i = 0; i < tiles.size(); i++) {
            Cell tile = tiles.get(i);
            if (i == tiles.size() - 1) {
                mergedTiles.add(tile);
            } else {
                Cell nextCell = tiles.get(i + 1);
                if (tile.equals(nextCell)) {
                    mergedTiles.add(tile.merge(nextCell));
                    mergedTiles.add(new Cell());
                    i++;
                } else {
                    mergedTiles.add(tile);
                }
            }
        }
        return mergedTiles;
    }
}
