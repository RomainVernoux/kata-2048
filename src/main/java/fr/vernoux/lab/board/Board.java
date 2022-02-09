package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {

    private List<Cell> content;

    public Board(RandomGenerator randomGenerator) {
        this.content = contentWithEmptyCells();
        addRandomTile(randomGenerator);
        addRandomTile(randomGenerator);
    }

    private Board() {
    }

    public int[][] getContent() {
        // TODO refactor this to simplify
        int[][] view = new int[4][4];
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                view[row][col] = content.get(row * 4 + col).asInt();
            }
        }
        return view;
    }

    public void moveLeft() {
        List<List<Cell>> rows = contentAsRows();
        rows.forEach(this::moveRowLeft);
    }

    private List<List<Cell>> contentAsRows() {
        // TODO refactor this to simplify
        List<List<Cell>> result = new ArrayList<>();
        for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
            ArrayList<Cell> row = new ArrayList<>();
            for (int colIndex = 0; colIndex < 4; colIndex++) {
                row.add(this.content.get(rowIndex * 4 + colIndex));
            }
            result.add(row);
        }
        return result;
    }

    private void moveRowLeft(List<Cell> row) {
//        List<Cell> movedRow = new ArrayList<>();
//        row.forEach(cell -> {
//            if (!cell.isEmpty()) {
//                movedRow.add(cell);
//            }
//        });
        // remplir

        // X X 2 X
        // X X 2 2
        // 2 X X 2
        // 2 2 2 2

        // while not all pushed left (
        // get next non-empty cell
        // push it left until left cell not empty
        // )

        // TODO refactor this to simplify

        int firstEmptyCellIndex = -1;
        for (int cellIndex = 0; cellIndex < 4; cellIndex++) {
            Cell cell = row.get(cellIndex);
            if (cell.isEmpty() && firstEmptyCellIndex == -1) {
                firstEmptyCellIndex = cellIndex;
            } else if (!cell.isEmpty() && firstEmptyCellIndex != -1) {
                Cell firstEmptyCell = row.get(firstEmptyCellIndex);
                firstEmptyCell.setTile(cell.asInt());
                cell.setTile(0);
                firstEmptyCellIndex++;
            }
        }
    }

    private List<Cell> contentWithEmptyCells() {
        return Stream.generate(Cell::new).limit(16).collect(Collectors.toList());
    }

    private void addRandomTile(RandomGenerator randomGenerator) {
        int emptyCells = countEmptyCells();
        int cellIndex = randomGenerator.randomInt(0, emptyCells);
        this.content.stream()
                .filter(Cell::isEmpty)
                .skip(cellIndex)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("Not enough empty cells"))
                .setTile(2);
    }

    private int countEmptyCells() {
        return (int) this.content.stream()
                .filter(Cell::isEmpty)
                .count();
    }

    public static Board fromContent(int[][] content) {
        // TODO refactor this to simplify
        Board board = new Board();
        board.content = Arrays.stream(content)
                .flatMapToInt(Arrays::stream)
                .mapToObj(Cell::new)
                .collect(Collectors.toList());
        return board;
    }
}
