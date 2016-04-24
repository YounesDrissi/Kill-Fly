package com.abdellah.pcsalon.myapplication.gestionListeSpinner;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by PC SALON on 29/03/2016.
 */
public class SpinnerAdapter<T> extends ArrayAdapter<String> {

    private String titre;
    public SpinnerAdapter(Context context, int resource,List<String> list,String titre) {
        super(context, resource,list);
        this.titre=titre;
    }

    @Override
    public String toString() {
        return titre;
    }
}
