package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;

public class mSensorEventListener extends Activity implements SensorEventListener {

    private Acceleromaze maze;
    private Activity context;

    private float gravityX;
    private float gravityY;

    private SensorManager mSensorManager;
    private Sensor mSensor;

    public mSensorEventListener(Context context, Acceleromaze maze) {
        this.context = (Activity)context;
        this.maze = maze;
        mSensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
    }

    @Override
    protected void onResume() {
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        super.onResume();
    }

    @Override
    public void onPause() {
        mSensorManager.unregisterListener(this, mSensor);
        super.onPause();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            if (event.values[0] > 1.0 && (event.values[0] * event.values[0] > event.values[1])) {
                maze.move(Acceleromaze.RIGHT);
            } else if (event.values[0] < -1.0 && (event.values[0] * event.values[0] > event.values[1] * event.values[1])) {
                maze.move(Acceleromaze.LEFT);
            } else if (event.values[1] > 1.0 && (event.values[1] * event.values[1] > event.values[0] * event.values[0])) {
                maze.move(Acceleromaze.UP);
            } else if (event.values[1] < -1.0 && (event.values[1] * event.values[1] > event.values[0] * event.values[0])) {
                maze.move(Acceleromaze.DOWN);
            }

        }

        if(maze.isGameComplete() || maze.isALoser()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getText(R.string.finished_title));
            LayoutInflater inflater = context.getLayoutInflater();
            View view = inflater.inflate(R.layout.finish, null);
            builder.setView(view);
            View closeButton = view.findViewById(R.id.closeGame);
            closeButton.setOnClickListener(new View.OnClickListener() {
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
