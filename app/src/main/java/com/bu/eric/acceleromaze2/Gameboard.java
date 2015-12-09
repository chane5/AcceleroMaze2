package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;

public class Gameboard extends View implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    private int width, height, boxWidth;

    private int mazeSizeX, mazeSizeY;

    float cellWidth, cellHeight;

    float totalCellWidth, totalCellHeight;

    private int mazeFinishX, mazeFinishY;
    private Acceleromaze maze;
    private Activity context;
    private Paint side, ball, pit, finish, background;

    int gravitySensitivityValue = 5;
    double diagonalSensitivityValue=0.25;

    public Gameboard(Context context, Acceleromaze maze) {
        super(context);
        this.maze = maze;
        this.context = (Activity)context;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
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

        int currentX = maze.getCurrentX(),currentY = maze.getCurrentY();
        //draw the ball
        canvas.drawCircle((currentX * totalCellWidth) + (totalCellWidth / 2),   //x of center
                (currentY * totalCellHeight) + (totalCellHeight / 2),  //y of center
                (totalCellWidth / 2),                           //radius
                ball);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        /*try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        if(event.sensor.getType() != Sensor.TYPE_ACCELEROMETER)
            return;
        boolean moved = false;
        if (event.values[0] < -gravitySensitivityValue) {
            moved = maze.move(Acceleromaze.RIGHT);
        }
        else if (event.values[0] > gravitySensitivityValue) {
            moved = maze.move(Acceleromaze.LEFT);
        }
        else if (event.values[1] < -gravitySensitivityValue) {
            moved = maze.move(Acceleromaze.UP);
        }
        else if (event.values[1] > gravitySensitivityValue) {
            moved = maze.move(Acceleromaze.DOWN);
        }
        //UPRIGHT=4
        else if ((event.values[0] < -diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] < -diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.UPRIGHT);
        }
        //DOWNRIGHT=5
        else if ((event.values[0] < -diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] > diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.DOWNRIGHT);
        }
        //UPLEFT=6
        else if ((event.values[0] > diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] < -diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.UPLEFT);
        }
        //DOWNLEFT=7
        else if ((event.values[0] > diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] > diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.DOWNLEFT);
        }
        if (moved) {
            invalidate();
            if (maze.isGameComplete()) {
                mSensorManager.unregisterListener(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getText(R.string.finished_title));
                LayoutInflater inflater = context.getLayoutInflater();
                View view = inflater.inflate(R.layout.finish, null);
                builder.setView(view);
                View closeButton = view.findViewById(R.id.closeGame);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View clicked) {
                        if (clicked.getId() == R.id.closeGame) {
                            context.finish();
                        }
                    }
                });
                AlertDialog finishDialog = builder.create();
                finishDialog.show();
            }
            else if(maze.isALoser()) {
                mSensorManager.unregisterListener(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getText(R.string.loser_title));
                LayoutInflater inflater = context.getLayoutInflater();
                View view = inflater.inflate(R.layout.loser, null);
                builder.setView(view);
                View closeButton = view.findViewById(R.id.closeGame);
                closeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View clicked) {
                        if (clicked.getId() == R.id.closeGame) {
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

