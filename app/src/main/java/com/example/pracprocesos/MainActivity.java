package com.example.pracprocesos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.nio.channels.SeekableByteChannel;


public class MainActivity extends AppCompatActivity {

    ImageView ivBoton;
    ImageView botonAjustes;
    ImageView ajustesFondo;
    Button botonJugar;
    ImageView botonInfo;
    Button botonhelp;
    TextView txtView;
    MediaPlayer mp;
    MediaPlayer mp2;
    private AudioManager audioManager;
    private SeekBar vol;
    ToggleButton Musica;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        ivBoton = (ImageView) findViewById(R.id.ivBoton);
        botonAjustes = (ImageView) findViewById(R.id.botonAjustes);
        botonInfo =  (ImageView) findViewById(R.id.botonInfo);
        botonJugar = (Button) findViewById(R.id.botonjugar);
        botonhelp = (Button) findViewById(R.id.botonhelp);
        Musica = (ToggleButton) findViewById(R.id.musica);
        mp = MediaPlayer.create(this, R.raw.sonidoboton);
        mp2 = MediaPlayer.create(this, R.raw.sonidofondo);
        //setVolumeControlStream(AudioManager.STREAM_MUSIC);
        //initControls();


        mp2.setLooping(true);
        mp2.start();
        ivBoton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                finish();
            }
        });
        botonAjustes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                mp.start();
                //vol.setVisibility(View.VISIBLE);
                startActivity(new Intent(MainActivity.this,ajustes_popup.class));

            }
        });
        botonInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                mp.start();
                startActivity(new Intent(MainActivity.this,info_popup.class));
            }
        });

        botonhelp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                mp.start();
                startActivity(new Intent(MainActivity.this,Help_popup.class));

            }
        });
        botonJugar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mp.start();
                Intent intent = new Intent(MainActivity.this,SelectVirusActivity.class);
                setContentView(R.layout.activity_select_virus);
                startActivity(intent);
                finish();
            }
        });

    }
    /*public void initControls()
    {
        try{
            vol = (SeekBar) findViewById(R.id.volumen);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            vol.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            vol.setProgress((audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)));
            vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

                public void onStopTrackingTouch(SeekBar seekBar){

                }
                public void onStartTrackingTouch(SeekBar seekBar){

                }
                public void onProgressChanged(SeekBar seekBar,int progress, boolean fromUser){
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onPause() {
        mp2.stop();
        mp2.release();
        super.onPause();
    }

    public void onResume(){
        mp2 = MediaPlayer.create(this, R.raw.sonidofondo);
        mp2.setLooping(false);
        mp2.start();
        super.onResume();
    }*/
}