package com.abdellah.pcsalon.myapplication.classPrincipal;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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
    private String siteAjoute="";
    private int posteAjoute;
    private int serieAjoute;

    private List<String> sites = new ArrayList<String>();
    private List<Integer> serie = new ArrayList<Integer>();
    private List<Integer> poste = new ArrayList<Integer>();

    private ArrayAdapter<String> dataAdapterSites ;
    private ArrayAdapter<Integer> arrayAdapterSerie;
    private ArrayAdapter<Integer> arrayAdapterPoste;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire_ajout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Elements de spinner
        spinnerSite = (Spinner) findViewById(R.id.spinnerSite);
        spinnerSerie = (Spinner) findViewById(R.id.spinnerSerie);
        spinnerPoste = (Spinner) findViewById(R.id.spinnerPoste);


        buttonSuivant =(Button)findViewById(R.id.buttonSuivant);
        buttonSuivant.setOnClickListener(clickListenerSuivant);


        addListenerOnSpinnerItemSelection();


        sites.add("Paris");
        sites.add("Toulouse");
        sites.add("Lyon");
        sites.add("Marseille");
        sites.add("Bordeaux");
        sites.add("Montpellier");


        serie.add(1);
        serie.add(2);
        serie.add(3);
        serie.add(4);


        for(int i=0;i<20;i++)
            poste.add(i);


        // Creating adapter for spinner
        dataAdapterSites = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sites);
        arrayAdapterSerie = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,serie);
        arrayAdapterPoste = new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_item,poste);

        // Drop down layout style - list view with radio button
        dataAdapterSites.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterSerie.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        arrayAdapterPoste.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerSite.setAdapter(dataAdapterSites);
        spinnerPoste.setAdapter(arrayAdapterPoste);
        spinnerSerie.setAdapter(arrayAdapterSerie);



        spinnerSerie.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                           int arg2, long arg3) {
                throw new RuntimeException("You long clicked an item!");
            }
        });



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
                serieAjoute =Integer.parseInt(input.getText().toString());
                serie.add(serieAjoute);

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
                poste.add(posteAjoute);

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


    private View.OnClickListener clickListenerSuivant=new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(ClassAjout.this, MainActivity.class);
            startActivity(intent);

        }
    };
}
