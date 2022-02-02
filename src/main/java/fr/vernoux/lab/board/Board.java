package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private final List<Cell> content;

    public Board(RandomGenerator randomGenerator) {
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
        return Stream.generate(Cell::new).limit(16).collect(Collectors.toList());
    }

    private void addTileToNthEmptyCell(int tileValue, int cellIndex) {
        this.content.stream()
                .filter(Cell::isEmpty)
                .skip(cellIndex)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not enough empty cells"))
                .setTile(tileValue);
    }
}
