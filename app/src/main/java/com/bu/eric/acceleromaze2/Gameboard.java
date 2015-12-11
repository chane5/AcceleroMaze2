package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

//attempt change
public class Gameboard extends View implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mAccelerometer;


    private int width, height, boxWidth;

    private int mazeSizeX, mazeSizeY;

    float cellWidth, cellHeight;

    float totalCellWidth, totalCellHeight;

    int orientation;
    public static final int UP = 0, DOWN = 1, RIGHT = 2, LEFT = 3, UPRIGHT=4, DOWNRIGHT=5, UPLEFT=6, DOWNLEFT=7;

    private int mazeFinishX, mazeFinishY;
    private Acceleromaze maze;
    private Activity context;
    private Paint side, ball, pit, finish, background;

    //default gSV=3 and dSV=0.5
    int gravitySensitivityValue = 3;
    double diagonalSensitivityValue = 0.25;

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

        int[][] traps = maze.getObstacles();
        for(int i = 0; i < mazeSizeY; i++) {
            for(int j = 0; j < mazeSizeX; j++){
                float x1 = j * totalCellWidth;
                float y1 = i * totalCellHeight;
                Log.d("log x,y values"," "+x1+" "+y1);
                if(traps[i][j]==1) {
                    canvas.drawCircle(x1 + (totalCellWidth / 2),
                            y1 + (totalCellWidth / 2),
                            (totalCellWidth / 2),
                            pit);
                }
                if(traps[i][j]==2){
                    float tleft, ttop, tright, tbottom;
                    tleft=(x1 * totalCellWidth);
                    ttop=(y1 * totalCellHeight);
                    tright=(x1 * totalCellWidth) + (totalCellWidth);
                    tbottom=(y1 * totalCellHeight) + (totalCellHeight);
                    Log.d("left,top,right,bottom"," "+tleft+" "+ttop+" "+tright+" "+tbottom);
                    Rect pointsImageBounds= new Rect( (int) tleft, (int) ttop, (int) tright, (int) tbottom);
                    Bitmap CPImage = BitmapFactory.decodeResource(getResources(), R.drawable.coinimage);
                    canvas.drawBitmap(CPImage, null, pointsImageBounds, null);
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
        //canvas.drawCircle(((currentX * totalCellWidth) + (totalCellWidth / 2)),   //x of center
        //        (currentY * totalCellHeight) + (totalCellHeight / 2),  //y of center
        //        (totalCellWidth / 2),                           //radius
        //        ball);
        float left, top, right, bottom;
        left=((currentX * totalCellWidth) + (totalCellWidth / 2))-(totalCellWidth / 2);
        top=((currentY * totalCellHeight) + (totalCellHeight / 2))-(totalCellWidth / 2);
        right=((currentX * totalCellWidth) + (totalCellWidth / 2))+(totalCellWidth / 2);
        bottom=((currentY * totalCellHeight) + (totalCellHeight / 2))+(totalCellWidth / 2);

        Rect imageBound= new Rect( (int) left, (int) top, (int) right, (int) bottom);
        Bitmap ssImage=null;
        if(orientation==UP) {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimageup);
        }
        else if(orientation==DOWN)
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimagedown);
        }
        else if(orientation==RIGHT)
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimageright);
        }
        else if(orientation==LEFT)
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimageleft);
        }
        else if(orientation==DOWNRIGHT)
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimagedownright);
        }
        else if(orientation==UPRIGHT)
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimageupright);
        }
        else if(orientation==DOWNLEFT)
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimagedownleft);
        }
        else if(orientation==UPLEFT)
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimageupleft);
        }
        else
        {
            ssImage = BitmapFactory.decodeResource(getResources(), R.drawable.ssimageup);
        }
        canvas.drawBitmap(ssImage, null, imageBound, null);
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
            orientation=RIGHT;
        }
        else if (event.values[0] > gravitySensitivityValue) {
            moved = maze.move(Acceleromaze.LEFT);
            orientation=LEFT;
        }
        else if (event.values[1] < -gravitySensitivityValue) {
            moved = maze.move(Acceleromaze.UP);
            orientation=UP;
        }
        else if (event.values[1] > gravitySensitivityValue) {
            moved = maze.move(Acceleromaze.DOWN);
            orientation=DOWN;
        }
        //UPRIGHT=4
        else if ((event.values[0] < -diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] < -diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.UPRIGHT);
            orientation=UPRIGHT;
        }
        //DOWNRIGHT=5
        else if ((event.values[0] < -diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] > diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.DOWNRIGHT);
            orientation=DOWNRIGHT;
        }
        //UPLEFT=6
        else if ((event.values[0] > diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] < -diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.UPLEFT);
            orientation=UPLEFT;
        }
        //DOWNLEFT=7
        else if ((event.values[0] > diagonalSensitivityValue*gravitySensitivityValue)&&(event.values[1] > diagonalSensitivityValue*gravitySensitivityValue))
        {
            moved=maze.move(Acceleromaze.DOWNLEFT);
            orientation=DOWNLEFT;
        }
        if (moved) {
            invalidate();
            if (maze.isGameComplete()) {
                Log.d("This is score:", " "+maze.getCoinPoints());
                mSensorManager.unregisterListener(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle(context.getText(R.string.finished_title));

                //score is here
                //TextView scoreValue = (TextView)findViewById(R.id.scoreShow);
                //scoreValue.setText("Scorebetter");

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
    //attempt to save.
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

