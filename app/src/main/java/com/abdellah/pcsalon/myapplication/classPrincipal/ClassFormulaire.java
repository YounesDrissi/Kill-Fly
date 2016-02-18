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
public class ClassFormulaire extends AppCompatActivity  implements OnItemSelectedListener{


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.layout_2);

            // Spinner element
            Spinner spinnerSite = (Spinner) findViewById(R.id.spinnerSite);
            Spinner spinnerPoste = (Spinner) findViewById(R.id.spinnerSerie);
            Spinner spinnerSerie = (Spinner) findViewById(R.id.spinnerPoste);

            // Spinner click listener
            spinnerSite.setOnItemSelectedListener(this);
            spinnerSerie.setOnItemSelectedListener(this);
            spinnerPoste.setOnItemSelectedListener(this);

            // Spinner Drop down elements
            List<String> categories = new ArrayList<String>();
            categories.add("Paris");
            categories.add("Toulouse");
            categories.add("Lyon");
            categories.add("Marseille");
            categories.add("Bordeaux");
            categories.add("Montpellier");

            // Creating adapter for spinner
            ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);

            // Drop down layout style - list view with radio button
            dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // attaching data adapter to spinner
            spinnerSite.setAdapter(dataAdapter);
        }

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            // On selecting a spinner item
            String item = parent.getItemAtPosition(position).toString();

            // Showing selected spinner item
            Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
        }
        public void onNothingSelected(AdapterView<?> arg0) {
        }
}
