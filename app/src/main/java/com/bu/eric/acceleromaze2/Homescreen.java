package com.bu.eric.acceleromaze2;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

public class Homescreen extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Button start = (Button)findViewById(R.id.start);
        Button exit = (Button)findViewById(R.id.exit);
        Button info = (Button)findViewById(R.id.info);
        start.setOnClickListener(this);
        exit.setOnClickListener(this);
        info.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info:
                Intent myIntent= new Intent(v.getContext(),infobox.class);
                startActivity(myIntent);
            case R.id.exit:
                finish();
                break;
            case R.id.start:
                String[] level = {"easy", "medium", "hard"};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle(getString(R.string.levelSelect));
                builder.setItems(level, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Intent game = new Intent(Homescreen.this, Gamelogic.class);
                        Acceleromaze maze = Mazegen.getMaze(item + 1);
                        game.putExtra("maze", maze);
                        startActivity(game);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
        }
    }
}