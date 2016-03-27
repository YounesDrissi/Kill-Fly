package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.abdellah.pcsalon.myapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by PC SALON on 19/02/2016.
 */
public class HisoriqueFormulaire  extends AppCompatActivity {

<<<<<<< HEAD
=======

>>>>>>> YounesBr
    private Spinner spinnerHistorique;

    public static View viewContainer;
    public static ListView listHistorique;

@Override
public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historique);

        // Spinner element
    spinnerHistorique = (Spinner) findViewById(R.id.spinnerHistorique);


        // Spinner click listener
        addListenerOnSpinnerItemSelection();

        // Spinner Drop down elements
        List<String> sites = new ArrayList<String>();
        sites.add("Paris");
        sites.add("Toulouse");
        sites.add("Lyon");
        sites.add("Marseille");
        sites.add("Bordeaux");
        sites.add("Montpellier");


        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapterSites = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sites);


        // Drop down layout style - list view with radio button
        dataAdapterSites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
    spinnerHistorique.setAdapter(dataAdapterSites);

    //list
    listHistorique = (ListView)findViewById(R.id.listView);
    viewContainer = findViewById(R.id.linearHistorique);
        }

public void addListenerOnSpinnerItemSelection() {

    spinnerHistorique.setOnItemSelectedListener(new HistoriqueClass(this));

}

}
