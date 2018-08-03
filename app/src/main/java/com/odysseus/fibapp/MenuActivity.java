package com.odysseus.fibapp;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MenuActivity extends AppCompatActivity {

    ImageButton ajustesib, avisosib, calendarioib, horarioib, notasib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        initializeButtons();
        setOnClickListenerMenuButtons();
    }

    protected void initializeButtons() {
        ajustesib = (ImageButton) findViewById(R.id.imageButton);
        avisosib = (ImageButton) findViewById(R.id.imageButton2);
        notasib = (ImageButton) findViewById(R.id.imageButton3);
        horarioib = (ImageButton) findViewById(R.id.imageButton5);
        calendarioib = (ImageButton) findViewById(R.id.imageButton4);
    }

    protected void setOnClickListenerMenuButtons() {
        ajustesib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustesib.setImageResource(R.drawable.ajustes_azul_oscuro_on_click120px);
            }
        });
        avisosib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avisosib.setImageResource(R.drawable.avisos_azul_oscuro_on_click120px);
            }
        });
        notasib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notasib.setImageResource(R.drawable.notas_azul_oscuro_on_click120px);
            }
        });
        horarioib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horarioib.setImageResource(R.drawable.horario_azul_oscuro_on_click120px);
            }
        });
        calendarioib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarioib.setImageResource(R.drawable.calendario_azul_oscuro_on_click120px);
            }
        });
    }
}