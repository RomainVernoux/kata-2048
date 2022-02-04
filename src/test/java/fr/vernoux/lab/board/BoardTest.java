package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;
import fr.vernoux.lab.doubles.CollisionRandomGeneratorDouble;
import fr.vernoux.lab.doubles.RandomGeneratorDouble;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BoardTest {

    @Test
    void board_size_is_4x4() {
        RandomGenerator randomGenerator = new RandomGeneratorDouble(1L);
        Board board = Board.newGame(randomGenerator);

        int[][] boardContent = board.getContent();
        assertThat(boardContent).hasDimensions(4, 4);
    }

    @Test
    void board_contains_two_tiles() {
        RandomGenerator randomGenerator = new RandomGeneratorDouble(1L);
        Board board = Board.newGame(randomGenerator);

        int[][] boardContent = board.getContent();
        assertThat(numberOfCellsWithTiles(boardContent)).isEqualTo(2);
    }

    @Test
    void first_two_tiles_are_placed_randomly_classicist() {
        RandomGenerator randomGenerator1 = new RandomGeneratorDouble(1L);
        Board board1 = Board.newGame(randomGenerator1);
        RandomGenerator randomGenerator2 = new RandomGeneratorDouble(2L);
        Board board2 = Board.newGame(randomGenerator2);

        assertThat(board1.getContent()).isNotEqualTo(board2.getContent());
    }

    @Nested
    class MoveLeftTests {
        @Test
        void tiles_with_same_value_that_touches_merge_into_one() {
            int[][] content = {
                    {2, 2, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
            Board board = Board.fromView(content);

            board.moveLeft();

            int[][] boardContent = board.getContent();
            assertThat(numberOfCellsWithTiles(boardContent)).isEqualTo(1);
            assertThat(boardContent[0][0]).isEqualTo(4);
        }

        @Test
        void moves_all_tiles_to_the_left() {
            int[][] content = {
                    {2, 0, 0, 4},
                    {0, 0, 2, 0},
                    {0, 8, 0, 8},
                    {0, 8, 4, 0}
            };
            Board board = Board.fromView(content);

            board.moveLeft();

            int[][] boardContent = board.getContent();
            int[][] expectedContent = {
                    {2, 4, 0, 0},
                    {2, 0, 0, 0},
                    {16, 0, 0, 0},
                    {8, 4, 0, 0}
            };
            assertThat(boardContent).isEqualTo(expectedContent);
        }
    }

    @Nested
    class MoveRightTests {
        @Test
        void tiles_with_same_value_that_touches_merge_into_one() {
            int[][] content = {
                    {2, 2, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
            Board board = Board.fromView(content);

            board.moveRight();

            int[][] boardContent = board.getContent();
            assertThat(numberOfCellsWithTiles(boardContent)).isEqualTo(1);
            assertThat(boardContent[0][3]).isEqualTo(4);
        }

        @Test
        void moves_all_tiles_to_the_right() {
            int[][] content = {
                    {2, 0, 0, 4},
                    {0, 0, 2, 0},
                    {0, 8, 0, 8},
                    {0, 8, 4, 0}
            };
            Board board = Board.fromView(content);

            board.moveRight();

            int[][] boardContent = board.getContent();
            int[][] expectedContent = {
                    {0, 0, 2, 4},
                    {0, 0, 0, 2},
                    {0, 0, 0, 16},
                    {0, 0, 8, 4}
            };
            assertThat(boardContent).isEqualTo(expectedContent);
        }
    }

    @Nested
    class MoveUpTests {
        @Test
        void tiles_with_same_value_that_touches_merge_into_one() {
            int[][] content = {
                    {2, 0, 0, 0},
                    {2, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
            Board board = Board.fromView(content);

            board.moveUp();

            int[][] boardContent = board.getContent();
            assertThat(numberOfCellsWithTiles(boardContent)).isEqualTo(1);
            assertThat(boardContent[0][0]).isEqualTo(4);
        }

        @Test
        void moves_all_tiles_to_the_top() {
            int[][] content = {
                    {2, 0, 0, 4},
                    {0, 0, 2, 0},
                    {0, 8, 0, 8},
                    {0, 8, 4, 0}
            };
            Board board = Board.fromView(content);

            board.moveUp();

            int[][] boardContent = board.getContent();
            int[][] expectedContent = {
                    {2, 16, 2, 4},
                    {0, 0, 4, 8},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
            assertThat(boardContent).isEqualTo(expectedContent);
        }
    }

    @Nested
    class MoveDownTests {
        @Test
        void tiles_with_same_value_that_touches_merge_into_one() {
            int[][] content = {
                    {2, 0, 0, 0},
                    {2, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 0}
            };
            Board board = Board.fromView(content);

            board.moveDown();

            int[][] boardContent = board.getContent();
            assertThat(numberOfCellsWithTiles(boardContent)).isEqualTo(1);
            assertThat(boardContent[3][0]).isEqualTo(4);
        }

        @Test
        void moves_all_tiles_to_the_top() {
            int[][] content = {
                    {2, 0, 0, 4},
                    {0, 0, 2, 0},
                    {0, 8, 0, 8},
                    {0, 8, 4, 0}
            };
            Board board = Board.fromView(content);

            board.moveDown();

            int[][] boardContent = board.getContent();
            int[][] expectedContent = {
                    {0, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 2, 4},
                    {2, 16, 4, 8}
            };
            assertThat(boardContent).isEqualTo(expectedContent);
        }
    }


    @Test
    @Disabled
    void first_two_tiles_are_placed_randomly_mockist() {
        RandomGenerator randomGenerator = mock(RandomGenerator.class);
        when(randomGenerator.randomInt(0, 4)).thenReturn(0, 1, 2, 3);

        Board board = Board.newGame(randomGenerator);

        int[][] boardContent = board.getContent();
        assertThat(boardContent[0][1]).isEqualTo(2);
        assertThat(boardContent[2][3]).isEqualTo(2);
    }

    @Test
    void random_board_generation_is_resilient_to_collisions() {
        RandomGenerator randomGenerator = new CollisionRandomGeneratorDouble();
        Board board = Board.newGame(randomGenerator);

        int[][] boardContent = board.getContent();
        assertThat(numberOfCellsWithTiles(boardContent)).isEqualTo(2);
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
