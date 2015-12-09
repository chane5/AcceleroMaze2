package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class Gamelogic extends Activity {

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        Acceleromaze maze = (Acceleromaze)extras.get("maze");
        Gameboard board = new Gameboard(this,maze);
        //new mSensorEventListener(this, maze, board);
        setContentView(board);
    }
}
