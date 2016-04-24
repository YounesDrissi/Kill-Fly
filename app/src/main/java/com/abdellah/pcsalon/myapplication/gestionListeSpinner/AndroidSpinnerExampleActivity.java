package com.abdellah.pcsalon.myapplication.gestionListeSpinner;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.abdellah.pcsalon.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC SALON on 29/03/2016.
 */

public class AndroidSpinnerExampleActivity extends Activity implements OnItemSelectedListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_activity);

        // Spinner element
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        // Creating adapter for spinner
        SpinnerAdapter<String> dataAdapter = new SpinnerAdapter<String>(this, android.R.layout.simple_spinner_item, categories,
                                                "categories");

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //spinner.get
        //spinner.setOnItemSelectedListener(new ItemChooser());
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();
        System.out.println("select simple sur " + item);
        // Showing selected spinner item
       // Toast.makeText(parent.getContext(),parent.getAdapter().toString()+": "+ item, Toast.LENGTH_LONG).show();
    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }



}