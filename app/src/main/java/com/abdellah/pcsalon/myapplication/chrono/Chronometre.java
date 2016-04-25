package com.abdellah.pcsalon.myapplication.chrono;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

import com.abdellah.pcsalon.myapplication.R;
import com.abdellah.pcsalon.myapplication.chrono.Cercle;

public class Chronometre extends Fragment {


    private Chronometer focus;
    private Button start, stop, restart;
    private static Cercle cercle;
    private long time = 0;
    private int etat = 0;

    public static Cercle getCercle() {
        return cercle;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.chronometer, container, false);
        start = (Button) rootView.findViewById(R.id.start_button);
        stop = (Button) rootView.findViewById(R.id.stop_button);
        restart = (Button) rootView.findViewById(R.id.reset_button);
        cercle = (Cercle) rootView.findViewById(R.id.cercle);
        focus = (Chronometer) rootView.findViewById(R.id.chronometer);

        start.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etat == 0) {
                    focus.setBase(SystemClock.elapsedRealtime() + time);
                    focus.start();
                    etat = 1;
                }
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (etat == 1) {
                    time = focus.getBase() - SystemClock.elapsedRealtime();
                    focus.stop();
                    etat = 0;
                }
            }
        });


        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                focus.setBase(SystemClock.elapsedRealtime());
            }
        });

        return rootView;
    }
}
