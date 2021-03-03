package com.example.pracprocesos;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.SeekBar;
import java.nio.channels.SeekableByteChannel;

public class ajustes_popup extends Activity {

    private AudioManager audioManager;
    private SeekBar vol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_ajustes);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls();

        DisplayMetrics medidasVentana = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(medidasVentana);

        int ancho = medidasVentana.widthPixels;
        int alto = medidasVentana.heightPixels;

        getWindow().setLayout((int)(alto), (int)(ancho * 0.5));
    }
    public void initControls() {
        try {
            vol = (SeekBar) findViewById(R.id.volumen);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            vol.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            vol.setProgress((audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)));
            vol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                public void onStopTrackingTouch(SeekBar seekBar) {

                }

                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
