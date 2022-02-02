package fr.vernoux.lab.board;

import fr.vernoux.lab.RandomGenerator;

public class Board {

    private int[][] content;

    public Board() {
        this.content = new int[4][4];
        this.content[0][1] = 2;
        this.content[1][0] = 2;
    }

    public Board(int [][]contentRandom) {
        this.content = contentRandom;
    }

    public Board(RandomGenerator randomGenerator) {
        this();
        int rowIndex = randomGenerator.randomInt(0, 4);
        int columnIndex = randomGenerator.randomInt(0, 4);
        this.content[rowIndex][columnIndex] = 2;
        rowIndex = randomGenerator.randomInt(0, 4);
        columnIndex = randomGenerator.randomInt(0, 4);
        this.content[rowIndex][columnIndex] = 2;
    }

    public int[][] getContent() {
        return content;
    }

    public void setContent(int[][] content) {
        this.content = content;
    }
    public void setPosition(int randomX, int randomY){
        this.content[randomX][randomY] = 2;
    }
}
