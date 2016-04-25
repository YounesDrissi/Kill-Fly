package com.abdellah.pcsalon.myapplication.gestionListeSpinner;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.abdellah.pcsalon.myapplication.Fragments;
import com.abdellah.pcsalon.myapplication.MainActivity;
import com.abdellah.pcsalon.myapplication.R;
import com.abdellah.pcsalon.myapplication.bdd.GestionBaseDeDonnees;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC SALON on 29/03/2016.
 */

public class AndroidSpinnerExampleActivity extends AppCompatActivity {


    private String siteAjoute="";
    private int posteAjoute;
    private int serieAjoute;
    private List<String> serie;
    private List<String> poste;
    private List<String> sites;
    private Button buttonSuivant;
    private Button buttonRetour;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_lieu_poste_serie);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // Spinner Lieu
        final Spinner spinnerLieu = (Spinner) findViewById(R.id.spinnerLieu);
        spinnerLieu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                System.out.println("select simple sur " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        sites = new ArrayList<String>();
        sites.add("Toulouse");
        sites.add("Paris");
        sites.add("Bollene");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getSiteList().add("Toulouse");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getSiteList().add("Paris");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getSiteList().add("Bollene");



        SpinnerAdapter<String> dataAdapter = new SpinnerAdapter<String>(this, android.R.layout.simple_spinner_item, sites,
                "site");
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLieu.setAdapter(dataAdapter);

        //sipinner Serie
        final Spinner spinnerSerie = (Spinner) findViewById(R.id.spinnerSerie);
        spinnerSerie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                System.out.println("select simple sur " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        serie=new ArrayList<String>();
        serie.add("0");
        serie.add("1");
        serie.add("2");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getSerieList().add("0");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getSerieList().add("1");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getSerieList().add("2");

        SpinnerAdapter<String> dataAdapterSerie = new SpinnerAdapter<String>(this, android.R.layout.simple_spinner_item, serie,
                "poste");
        dataAdapterSerie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSerie.setAdapter(dataAdapterSerie);

        //sipinner Poste
        final Spinner spinnerPoste = (Spinner) findViewById(R.id.spinnerPoste);
        spinnerPoste.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                System.out.println("select simple sur " + item);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        poste=new ArrayList<String>();
        poste.add("0");
        poste.add("1");
        poste.add("2");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getPosteList().add("0");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getPosteList().add("1");
        GestionBaseDeDonnees.getGestionBaseDeDonnees().getPosteList().add("2");


        SpinnerAdapter<String> dataAdapterPoste= new SpinnerAdapter<String>(this, android.R.layout.simple_spinner_item, poste,
                "categories");
        dataAdapterPoste.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPoste.setAdapter(dataAdapterPoste);


        //listner button
        buttonSuivant =(Button)findViewById(R.id.buttonSuivant);
        buttonSuivant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionBaseDeDonnees.setSite(spinnerLieu.getSelectedItem().toString());
                GestionBaseDeDonnees.setPoste(spinnerPoste.getSelectedItem().toString());
                GestionBaseDeDonnees.setSerie(spinnerSerie.getSelectedItem().toString());
                Intent intent = new Intent(AndroidSpinnerExampleActivity.this, Fragments.class);
                startActivity(intent);
            }
        });

        buttonRetour= (Button) findViewById(R.id.buttonRetour);
        buttonRetour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AndroidSpinnerExampleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }


    private void ajouterSerie() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Série");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                serieAjoute = Integer.parseInt(input.getText().toString());
                serie.add(input.getText().toString());
                GestionBaseDeDonnees.getGestionBaseDeDonnees().getSerieList().add(input.getText().toString());

            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    private void ajouterPoste() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Poste");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                posteAjoute = Integer.parseInt(input.getText().toString());
                poste.add(input.getText().toString());
                GestionBaseDeDonnees.getGestionBaseDeDonnees().getPosteList().add(input.getText().toString());

            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }


    private void ajouterSite() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nom du Site");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                siteAjoute = input.getText().toString();
                sites.add(siteAjoute);
                GestionBaseDeDonnees.getGestionBaseDeDonnees().getSiteList().add(input.getText().toString());
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            case R.id.ajouterPoste:
                ajouterPoste();
                return true;
            case R.id.ajouterSerie:
                ajouterSerie();
                return true;
            case R.id.ajouterSite:
                ajouterSite();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}