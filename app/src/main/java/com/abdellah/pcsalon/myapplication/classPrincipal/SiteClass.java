package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

/**
 * Created by Younes on 19/02/2016.
 */
public class SiteClass implements AdapterView.OnItemSelectedListener {

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                parent.getItemAtPosition(pos).toString() + " Selectionné",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
