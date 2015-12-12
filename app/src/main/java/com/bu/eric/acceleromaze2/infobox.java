package com.bu.eric.acceleromaze2;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class infobox extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_infobox);

        Button back = (Button)findViewById(R.id.back);
        back.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.back:
                Intent myIntent= new Intent(v.getContext(),Homescreen.class);
                startActivity(myIntent);
        }
    }
}