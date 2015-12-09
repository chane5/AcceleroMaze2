package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class mSensorEventListener extends Activity implements SensorEventListener {

    private Gameboard board;
    private Acceleromaze maze;
    private Activity context;

    private SensorManager mSensorManager;

    public mSensorEventListener(Context context, Acceleromaze maze, Gameboard board) {
        this.board = board;
        this.context = (Activity)context;
        this.maze = maze;
        Log.d("test", "constructed");
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensorManager.registerListener(this,mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY),
                SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        boolean moved = false;
        maze.move(1);
        board.invalidate();
        /*if (event.values[0] > 1.0 && (event.values[0] * event.values[0] > event.values[1])) {
            moved = maze.move(Acceleromaze.RIGHT);
        } else if (event.values[0] < -1.0 && (event.values[0] * event.values[0] > event.values[1] * event.values[1])) {
            moved = maze.move(Acceleromaze.LEFT);
        } else if (event.values[1] > 1.0 && (event.values[1] * event.values[1] > event.values[0] * event.values[0])) {
            moved = maze.move(Acceleromaze.UP);
        } else if (event.values[1] < -1.0 && (event.values[1] * event.values[1] > event.values[0] * event.values[0])) {
            moved = maze.move(Acceleromaze.DOWN);
        }*/

        if (moved) {
            board.invalidate();
            if (maze.isGameComplete() || maze.isALoser()) {
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
        }
    }

    @Override
    public void onAccuracyChanged(Sensor mSensor, int accuracy) {

    }
}
