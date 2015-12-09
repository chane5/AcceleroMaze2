package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.SensorEventListener;
import android.view.View;

public class Gameboard extends View {


    private int width, height, boxWidth;

    private int mazeSizeX, mazeSizeY;

    float cellWidth, cellHeight;

    float totalCellWidth, totalCellHeight;

    private int mazeFinishX, mazeFinishY;
    private Acceleromaze maze;
    private Paint side, ball, pit, finish, background;

    public Gameboard(Context context, Acceleromaze maze) {
        super(context);
        this.maze = maze;
        mazeFinishX = maze.getFinalX();
        mazeFinishY = maze.getFinalY();
        mazeSizeX = maze.getMazeWidth();
        mazeSizeY = maze.getMazeHeight();
        side = new Paint();
        side.setColor(getResources().getColor(R.color.bord));
        ball = new Paint();
        ball.setColor(getResources().getColor(R.color.player));
        pit = new Paint();
        pit.setColor(getResources().getColor(R.color.ahh));
        finish = new Paint();
        finish.setColor(getResources().getColor(R.color.end));
        background = new Paint();
        background.setColor(getResources().getColor(R.color.back));
        setFocusable(true);
        this.setFocusableInTouchMode(true);
    }
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        width = w;
        height = h;         //for now square mazes
        boxWidth = 20;          //for now 1 pixel wide walls
        cellWidth = (width - ((float)mazeSizeX*boxWidth)) / mazeSizeX;
        totalCellWidth = cellWidth+boxWidth;
        cellHeight = (height - ((float)mazeSizeY*boxWidth)) / mazeSizeY;
        totalCellHeight = cellHeight+boxWidth;
        ball.setTextSize(cellHeight * 0.75f);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    protected void onDraw(Canvas canvas) {
        //fill in the background
        canvas.drawRect(0, 0, width, height, background);

        boolean[][] walls = maze.getBorders();
        //iterate over the boolean arrays to draw walls
        for(int i = 0; i < mazeSizeY; i++) {
            for(int j = 0; j < mazeSizeX; j++){
                float x = j * totalCellWidth;
                float y = i * totalCellHeight;
                if(walls[i][j]) {
                    canvas.drawRect(x,   //start X
                            y,               //start Y
                            x + totalCellWidth,   //stop X
                            y + totalCellHeight,  //stop Y
                            side);
                }
            }
        }

        boolean[][] traps = maze.getHoles();
        for(int i = 0; i < mazeSizeY; i++) {
            for(int j = 0; j < mazeSizeX; j++){
                float x1 = j * totalCellWidth;
                float y1 = i * totalCellHeight;
                if(traps[i][j]) {
                    canvas.drawCircle(x1 + (totalCellWidth / 2),
                            y1 + (totalCellWidth / 2),
                            (totalCellWidth / 2),
                            pit);
                }
            }
        }

        //draw the finishing point indicator
        canvas.drawCircle((mazeFinishX * totalCellWidth) + (totalCellWidth / 2),
                (mazeFinishY * totalCellHeight) + (totalCellHeight / 2),
                (totalCellWidth / 2),
                finish);
        while (!maze.isALoser() && !maze.isGameComplete()) {
            if(maze.move(0) || maze.move(1) || maze.move(2) || maze.move(3)) {
                int currentX = maze.getCurrentX(),currentY = maze.getCurrentY();
                invalidate();
                //draw the ball
                canvas.drawCircle((currentX * totalCellWidth) + (totalCellWidth / 2),   //x of center
                        (currentY * totalCellHeight) + (totalCellHeight / 2),  //y of center
                        (totalCellWidth / 2),                           //radius
                        ball);
            }
        }
    }
}

