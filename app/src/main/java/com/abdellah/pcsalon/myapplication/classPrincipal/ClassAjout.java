package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.abdellah.pcsalon.myapplication.MainActivity;
import com.abdellah.pcsalon.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Younes on 19/02/2016.
 */
public class ClassAjout extends AppCompatActivity {


    private Spinner spinnerSite,spinnerSerie,spinnerPoste;
    private Button buttonSuivant;


    private View.OnClickListener clickListenerSuivant=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ClassAjout.this, MainActivity.class);
            startActivity(intent);

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire_ajout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Spinner element
        spinnerSite = (Spinner) findViewById(R.id.spinnerSite);
        spinnerSerie = (Spinner) findViewById(R.id.spinnerSerie);
        spinnerPoste = (Spinner) findViewById(R.id.spinnerPoste);

        buttonSuivant =(Button)findViewById(R.id.buttonSuivant);

        buttonSuivant.setOnClickListener(clickListenerSuivant);

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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void addListenerOnSpinnerItemSelection() {
        spinnerPoste.setOnItemSelectedListener(new PosteClass());
        spinnerSerie.setOnItemSelectedListener(new SerieClass());
        spinnerSite.setOnItemSelectedListener(new SiteClass());
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){
            case R.id.ajouterPoste:
                //ajouterPoste();
                return true;
            case R.id.ajouterSerie:
                //ajouterSerie();
                return true;
            case R.id.ajouterSite:
                //ajouterSite();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
