package com.example.pracprocesos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SelectVirusActivity extends AppCompatActivity {

    Button botonComenzar;
    EditText nombre;
    String nombreVirus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_virus);

        botonComenzar = (Button) findViewById(R.id.botoncomenzar);
        nombre = (EditText)findViewById(R.id.nombreVirus);
        nombre.setText("Covid-19");
        botonComenzar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                nombreVirus = nombre.getText().toString();

                Intent intent = new Intent(SelectVirusActivity.this,MapActivity.class);
                intent.putExtra("virus",nombreVirus);
                setContentView(R.layout.activity_map);
                startActivity(intent);
                finish();
            }
        });



    }
}