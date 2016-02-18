package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Younes on 18/02/2016.
 */
public class SerieClass implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),"Serie "+
                parent.getItemAtPosition(pos).toString()+" Selectionnée",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
