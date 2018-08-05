package com.odysseus.fibapp.Fragments;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;

import com.odysseus.fibapp.R;

public class MenuActivity extends Fragment {

    ImageButton ajustesib, avisosib, calendarioib, horarioib, notasib;
    View v;
    Fragment f;
    public MenuActivity() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = inflater.inflate(R.layout.activity_menu, container, false);

        initializeButtons();
        setOnClickListenerMenuButtons();

        return v;
    }

    protected void initializeButtons() {
        ajustesib = (ImageButton) v.findViewById(R.id.imageButton);
        avisosib = (ImageButton) v.findViewById(R.id.imageButton2);
        notasib = (ImageButton) v.findViewById(R.id.imageButton3);
        horarioib = (ImageButton) v.findViewById(R.id.imageButton5);
        calendarioib = (ImageButton) v.findViewById(R.id.imageButton4);
    }

    protected void setOnClickListenerMenuButtons() {
        ajustesib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ajustesib.setImageResource(R.drawable.ajustes_azul_oscuro_on_click120px);
                f = new AjustesFragment();
                startFragment();
            }
        });
        avisosib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                avisosib.setImageResource(R.drawable.avisos_azul_oscuro_on_click120px);
                f = new AvisosFragment();
                startFragment();
            }
        });
        notasib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notasib.setImageResource(R.drawable.notas_azul_oscuro_on_click120px);
                f = new Notas();
                startFragment();
            }
        });
        horarioib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                horarioib.setImageResource(R.drawable.horario_azul_oscuro_on_click120px);
                f = new HorarioFragment();
                startFragment();
            }
        });
        calendarioib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarioib.setImageResource(R.drawable.calendario_azul_oscuro_on_click120px);
                f = new CalendarioFragment();
                startFragment();
            }
        });
    }

    protected void startFragment() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //instead of FragmentManager fm = getSupportFragmentManager();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.fragment_container,f);
                //add the fragment to the backstack in order to come back to the menu by the time you press back
                //source: https://stackoverflow.com/questions/22101442/android-app-closes-when-back-button-pressed
                ft.addToBackStack(null);
                ft.commit();
            }
        }, 100);
    }
}