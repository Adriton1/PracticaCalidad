package com.example.pracprocesos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class help_1 extends AppCompatActivity {

        Button sig;
        ImageView casa;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_1);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        sig = (Button) findViewById(R.id.sig);
        casa = (ImageView) findViewById(R.id.casa);

        sig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(help_1.this,help_2.class);
                setContentView(R.layout.activity_help_2);
                startActivity(intent);
                finish();
            }
        });

        casa.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(help_1.this,MainActivity.class);
                setContentView(R.layout.activity_main);
                startActivity(intent);
                finish();
            }
        });
    }
}