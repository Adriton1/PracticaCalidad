package com.example.pracprocesos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class help_2 extends AppCompatActivity {

    Button ant;
    Button sig;
    ImageView casa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_2);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ant = (Button) findViewById(R.id.ant);
        sig = (Button) findViewById(R.id.sig);
        casa = (ImageView) findViewById(R.id.casa);

        ant.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(help_2.this,help_1.class);
                setContentView(R.layout.activity_help_1);
                startActivity(intent);
                finish();
            }
        });
        sig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(help_2.this,help_3.class);
                setContentView(R.layout.activity_help_3);
                startActivity(intent);
                finish();
            }
        });
        casa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(help_2.this,MainActivity.class);
                setContentView(R.layout.activity_main);
                startActivity(intent);
                finish();
            }
        });
    }
}