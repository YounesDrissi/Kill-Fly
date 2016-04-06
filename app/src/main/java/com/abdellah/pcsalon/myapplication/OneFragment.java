package com.abdellah.pcsalon.myapplication;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

public class OneFragment extends Fragment  {



    static Chronometer focus;
    Button start, stop, restart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.chronometer, container, false);
        start = (Button) rootView.findViewById(R.id.start_button);
        stop = (Button) rootView.findViewById(R.id.stop_button);
        restart = (Button) rootView.findViewById(R.id.reset_button);

        focus = (Chronometer) rootView.findViewById(R.id.chronometer);

        start.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                focus.start();
            }
        });


        stop.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                focus.stop();
            }
        });


        restart.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                focus.setBase(SystemClock.elapsedRealtime());
            }
        });

        return rootView;
    }
}
