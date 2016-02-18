package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.abdellah.pcsalon.myapplication.R;

import java.util.ArrayList;
import java.util.List;


import android.view.View;
import android.widget.AdapterView;

import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;


/**
 * Created by Younes on 11/02/2016.
 */
public class ClassFormulaire extends AppCompatActivity {


    private Spinner spinnerSite,spinnerSerie,spinnerPoste;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_2);

            // Spinner element
            spinnerSite = (Spinner) findViewById(R.id.spinnerSite);
            spinnerSerie = (Spinner) findViewById(R.id.spinnerSerie);
            spinnerPoste = (Spinner) findViewById(R.id.spinnerPoste);

            // Spinner click listener
            addListenerOnSpinnerItemSelection();
            /*spinnerSite.setOnItemSelectedListener(this);
            spinnerPoste.setOnItemSelectedListener(this);
            spinnerSerie.setOnItemSelectedListener(this);*/

            // Spinner Drop down elements
            List<String> sites = new ArrayList<String>();
            sites.add("Paris");
            sites.add("Toulouse");
            sites.add("Lyon");
            sites.add("Marseille");
            sites.add("Bordeaux");
            sites.add("Montpellier");

            List<Integer> serie = new ArrayList<Integer>();
            serie.add(1);
            serie.add(2);
            serie.add(3);
            serie.add(4);

            List<Integer> poste = new ArrayList<Integer>();
            poste.add(1);
            poste.add(2);
            poste.add(3);
            poste.add(4);
            poste.add(5);
            poste.add(6);
            poste.add(7);
            poste.add(8);
            poste.add(9);
            poste.add(10);
            poste.add(11);
            poste.add(12);
            poste.add(13);
            poste.add(14);
            poste.add(15);
            poste.add(16);
            poste.add(17);
            poste.add(18);
            poste.add(19);
            poste.add(20);
            poste.add(21);
            poste.add(22);
            poste.add(23);
            poste.add(24);

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapterSites = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sites);
            ArrayAdapter<Integer> arrayAdapterSerie = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,serie);
            ArrayAdapter<Integer> arrayAdapterPoste = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,poste);

            // Drop down layout style - list view with radio button
            dataAdapterSites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterSerie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            arrayAdapterPoste.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerSite.setAdapter(dataAdapterSites);
            spinnerPoste.setAdapter(arrayAdapterPoste);
            spinnerSerie.setAdapter(arrayAdapterSerie);
        }

    public void addListenerOnSpinnerItemSelection() {
        spinnerPoste.setOnItemSelectedListener(new PosteClass());
        spinnerSerie.setOnItemSelectedListener(new SerieClass());
        spinnerSite.setOnItemSelectedListener(new SiteClass());
    }
        /*@Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }*/
}
