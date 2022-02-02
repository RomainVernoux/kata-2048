package fr.vernoux.lab.board;

import org.junit.jupiter.api.Test;

import java.util.Random;

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
        // GIVEN
        Board boardExpected = new Board();
        boardExpected.setContent(new int [4][4]);

        // WHEN
        boardExpected = positionRandomely2Tiles(boardExpected);

        // THEN
        for (int i= 0; i<3; i++){
            Board boardActual = new Board();
            boardActual.setContent(new int [4][4]);
            System.out.println(i);
            boardActual = positionRandomely2Tiles(boardActual);
            // il a 1/240 chance de tomber sur le meme board mutiplie par 3 = 3/240
            assertThat(boardExpected.getContent()).isNotEqualTo(boardActual.getContent());
        }

    }

    public Board positionRandomely2Tiles(Board board){
        Random randomNumber = new Random();
        int randomX = randomNumber.nextInt(4);
        int randomY = randomNumber.nextInt(4);
        for (int i=0; i<2; i++){
            // si jamais la place est déja occupé
            while(board.getContent()[randomX][randomY] != 0) {
                randomX = randomNumber.nextInt(4);
                randomY = randomNumber.nextInt(4);
            }
            board.setPosition(randomX,randomY);
        }
        return board;
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
