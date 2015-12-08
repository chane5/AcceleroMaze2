package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
//import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;


/**
 * Created by Matt on 12/6/15.
 */

public class Gameboard extends View implements SensorEventListener{


    private int width, height, boxWidth;

    private int mazeSizeX, mazeSizeY;

    float cellWidth, cellHeight;

    float totalCellWidth, totalCellHeight;

    private int mazeFinishX, mazeFinishY;
    private Acceleromaze maze;
    private Activity context;
    private Paint side, ball, finish, background;

    public Gameboard(Context context, Acceleromaze maze) {
        super(context);
        this.context = (Activity)context;
        this.maze = maze;
        mazeFinishX = maze.getFinalX();
        mazeFinishY = maze.getFinalY();
        mazeSizeX = maze.getMazeWidth();
        mazeSizeY = maze.getMazeHeight();
        side = new Paint();
        side.setColor(getResources().getColor(R.color.bord));
        ball = new Paint();
        ball.setColor(getResources().getColor(R.color.player));
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
        for(int i = 0; i < mazeSizeX; i++) {
            for(int j = 0; j < mazeSizeY; j++){
                float x = j * totalCellWidth;
                float y = i * totalCellHeight;
                if(j < mazeSizeX - 1 && walls[i][j]) {
                    canvas.drawRect(x,   //start X
                            y,               //start Y
                            x + totalCellWidth,   //stop X
                            y + totalCellHeight,  //stop Y
                            side);
                }
            }
        }

        int currentX = maze.getCurrentX(),currentY = maze.getCurrentY();
        //draw the ball
        canvas.drawCircle((currentX * totalCellWidth) + (totalCellWidth / 2),   //x of center
                (currentY * totalCellHeight) + (totalCellHeight / 2),  //y of center
                (totalCellWidth / 2),                           //radius
                ball);
        //draw the finishing point indicator
        canvas.drawCircle((mazeFinishX * totalCellWidth) + (totalCellWidth / 2),
                (mazeFinishY * totalCellHeight) + (totalCellHeight / 2),
                (totalCellWidth / 2),
                finish);
    }
    /*@Override
    public boolean onKeyDown(int keyCode, KeyEvent evt) {
        boolean moved = false;
        switch(keyCode) {
            case KeyEvent.KEYCODE_DPAD_UP:
                moved = maze.move(Acceleromaze.UP);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                moved = maze.move(Acceleromaze.DOWN);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                moved = maze.move(Acceleromaze.RIGHT);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                moved = maze.move(Acceleromaze.LEFT);
                break;
            default:
                return super.onKeyDown(keyCode,evt);
        }
        if(moved) {
            //the ball was moved so we'll redraw the view
            invalidate();
            if(maze.isGameComplete()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getText(R.string.finished_title));
                LayoutInflater inflater = context.getLayoutInflater();
                View view = inflater.inflate(R.layout.finish, null);
                builder.setView(view);
                View closeButton = view.findViewById(R.id.closeGame);
                closeButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View clicked) {
                        if(clicked.getId() == R.id.closeGame) {
                            context.finish();
                        }
                    }
                });
                AlertDialog finishDialog = builder.create();
                finishDialog.show();
            }
        }
        return true;
    }*/

    @Override
    public void onSensorChanged(SensorEvent event) {
        boolean moved = false;

        if(event.values[0] > 1.0 && (event.values[0]*event.values[0] > event.values[1])) {
            moved = maze.move(Acceleromaze.RIGHT);
        }
        else if(event.values[0] < -1.0 && (event.values[0]*event.values[0] > event.values[1]*event.values[1])) {
            moved = maze.move(Acceleromaze.LEFT);
        }
        else if(event.values[1] > 1.0 && (event.values[1]*event.values[1] > event.values[0]*event.values[0])) {
            moved = maze.move(Acceleromaze.UP);
        }
        else if(event.values[1] < -1.0 && (event.values[1]*event.values[1] > event.values[0]*event.values[0])) {
            moved = maze.move(Acceleromaze.DOWN);
        }

        if(moved) {
            //the ball was moved so we'll redraw the view
            invalidate();
            if(maze.isGameComplete() || maze.isALoser()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getText(R.string.finished_title));
                LayoutInflater inflater = context.getLayoutInflater();
                View view = inflater.inflate(R.layout.finish, null);
                builder.setView(view);
                View closeButton = view.findViewById(R.id.closeGame);
                closeButton.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View clicked) {
                        if(clicked.getId() == R.id.closeGame) {
                            context.finish();
                        }
                    }
                });
                AlertDialog finishDialog = builder.create();
                finishDialog.show();
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

