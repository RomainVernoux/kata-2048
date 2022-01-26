package fr.vernoux.lab.board;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BoardTest {

    @Test
    void board_size_is_4x4() {
        Board board = new Board();

        int[][] boardContent = board.getContent();
        assertThat(boardContent).hasDimensions(4, 4);
    }

    @Test
    void board_contains_two_tiles() {
        Board board = new Board();

        int[][] boardContent = board.getContent();
        assertThat(numberOfCellsWithTiles(boardContent)).isEqualTo(2);
    }

    @Test
    void first_two_tiles_are_placed_randomly() {

    }

    private int numberOfCellsWithTiles(int[][] boardContent) {
        int numberOfCellsWithTiles = 0;
        for (int[] row : boardContent) {
            for (int cell : row) {
                if (cell > 0) {
                    numberOfCellsWithTiles++;
                }
            }
        }
        return numberOfCellsWithTiles;
    }
}
