package com.bu.eric.acceleromaze2;

import android.app.Activity;

import java.io.Serializable;

public class Acceleromaze extends Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3;

    private boolean[][] borders;
    private boolean[][] holes;
    private int sizeX, sizeY;
    private int currentX, currentY;
    private int finalX, finalY;
    private boolean gameComplete;
    private boolean died = false;


    public boolean move(int direction) {
        boolean moved = false;
        if(direction == UP) {
            if(currentY != 1 && !borders[currentY-1][currentX]) {
                currentY--;
                moved = true;
                if(holes[currentY][currentX]){
                    died = true;
                }
            }
        }
        if(direction == DOWN) {
            if(currentY != sizeY-1 && !borders[currentY+1][currentX]) {
                currentY++;
                moved = true;
                if(holes[currentY][currentX]){
                    died = true;
                }
            }
        }
        if(direction == RIGHT) {
            if(currentX != sizeX-1 && !borders[currentY][currentX+1]) {
                currentX++;
                moved = true;
                if(holes[currentY][currentX]){
                    died = true;
                }
            }
        }
        if(direction == LEFT) {
            if(currentX != 1 && !borders[currentY][currentX-1]) {
                currentX--;
                moved = true;
                if(holes[currentY][currentX]){
                    died = true;
                }
            }
        }
        if(moved) {
            if(currentX == finalX && currentY == finalY) {
                gameComplete = true;
            }
        }
        return moved;
    }

    public int getMazeWidth() {
        return sizeX;
    }
    public int getMazeHeight() {
        return sizeY;
    }
    public boolean isGameComplete() {
        return gameComplete;
    }

    public boolean isALoser() {
        return died;
    }

    public void setStartPosition(int x, int y) {
        currentX = x;
        currentY = y;
    }
    public int getFinalX() {
        return finalX;
    }

    public int getFinalY() { return finalY; }

    public void setFinalPosition(int x, int y) {
        finalX = x;
        finalY = y;
    }
    public int getCurrentX() {
        return currentX;
    }
    public int getCurrentY() {
        return currentY;
    }
    public boolean[][] getBorders() {
        return borders;
    }

    public void setBorders(boolean[][] lines) {
        borders = lines;
        sizeX = borders[0].length;
        sizeY = borders.length;
    }

    public boolean[][] getHoles() {
        return holes;
    }

    public void setHoles(boolean[][] lines) {
        holes = lines;
    }

}
