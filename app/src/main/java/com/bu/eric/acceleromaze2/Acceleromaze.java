package com.bu.eric.acceleromaze2;

import android.app.Activity;

import java.io.Serializable;

public class Acceleromaze extends Activity implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, UPRIGHT=4, DOWNRIGHT=5, UPLEFT=6, DOWNLEFT=7;

    private boolean[][] borders;
    private int[][] obstacles;
    private int sizeX, sizeY;
    private int currentX, currentY;
    private int finalX, finalY;
    private boolean gameComplete;
    private boolean died = false;
    int coinPoints = 0;


    public boolean move(int direction) {
        boolean moved = false;
        if(direction == UP) {
            if(currentY != 1 && !borders[currentY-1][currentX]) {
                currentY--;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
                }

            }
        }
        if(direction == DOWN) {
            if(currentY != sizeY-1 && !borders[currentY+1][currentX]) {
                currentY++;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
                }
            }
        }
        if(direction == RIGHT) {
            if(currentX != sizeX-1 && !borders[currentY][currentX+1]) {
                currentX++;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
                }
            }
        }
        if(direction == LEFT) {
            if(currentX != 1 && !borders[currentY][currentX-1]) {
                currentX--;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
                }
            }
        }
        if(direction==UPRIGHT)
        {
            if(currentY != 1 && currentX != sizeX-1 && !borders[currentY-1][currentX+1] && !borders[currentY-1][currentX] && !borders[currentY][currentX+1]) {
                currentX++;
                currentY--;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
                }
            }
        }
        if(direction==DOWNRIGHT)
        {
            if(currentY != sizeY-1 && currentX != sizeX-1 && !borders[currentY+1][currentX+1] && !borders[currentY+1][currentX] && !borders[currentY][currentX+1]) {
                currentX++;
                currentY++;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
                }
            }
        }
        if(direction==UPLEFT)
        {
            if(currentY != 1 && currentX != 1 && !borders[currentY-1][currentX-1] && !borders[currentY-1][currentX] && !borders[currentY][currentX-1]) {
                currentX--;
                currentY--;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
                }
            }
        }
        if(direction==DOWNLEFT)
        {
            if(currentY != sizeY-1 && currentX != 1 && !borders[currentY+1][currentX-1] && !borders[currentY+1][currentX] && !borders[currentY][currentX-1]) {
                currentX--;
                currentY++;
                moved = true;
                if(obstacles[currentY][currentX]==1){
                    died = true;
                }
                else if(obstacles[currentY][currentX]==2){
                    coinPoints++;
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

    public int[][] getObstacles() {
        return obstacles;
    }

    public void setObstacles(int[][] lines) {
        obstacles = lines;
    }

    public int getCoinPoints(){return coinPoints;}

}
