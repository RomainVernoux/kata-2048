package fr.vernoux.lab.board;

import java.util.ArrayList;

public class BoardForTest extends Board {
    public BoardForTest(int[][] view) {
        content = new ArrayList<>(16);
        for (int row = 0; row < 4; row++) {
            for (int col = 0; col < 4; col++) {
                content.add(new Cell(view[row][col]));
            }
        }
    }
}
