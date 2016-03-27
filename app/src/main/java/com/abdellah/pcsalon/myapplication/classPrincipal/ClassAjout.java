package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.SparseArray;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.abdellah.pcsalon.myapplication.MainActivity;
import com.abdellah.pcsalon.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Younes on 19/02/2016.
 */
public class ClassAjout extends AppCompatActivity {


    private SparseArray<Groupe> groups = new SparseArray<Groupe>();
    private static Boolean longClick=false;

    private Button buttonSuivant;
    private String siteAjoute="";
    private int posteAjoute;
    private int serieAjoute;

    private List<String> sites = new ArrayList<String>();
    private List<Integer> serie = new ArrayList<Integer>();
    private List<Integer> poste = new ArrayList<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulaire_ajout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*// Elements de spinner
        spinnerSite = (Spinner) findViewById(R.id.spinnerSite);
        spinnerSerie = (Spinner) findViewById(R.id.spinnerSerie);
        spinnerPoste = (Spinner) findViewById(R.id.spinnerPoste);
*/
        createDataSite();

        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listeSite);
        registerForContextMenu(listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this, groups);
        listView.setAdapter(adapter);
        buttonSuivant =(Button)findViewById(R.id.buttonSuivant);
        buttonSuivant.setOnClickListener(clickListenerSuivant);


        //addListenerOnSpinnerItemSelection();

        /*sites.add("Paris");
        sites.add("Toulouse");
        sites.add("Lyon");
        sites.add("Marseille");
        sites.add("Bordeaux");
        sites.add("Montpellier");

        for(int i=0;i<5;i++)
            serie.add(i);

        for(int i=0;i<31;i++)
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
        });*/
    }

   /* public void addListenerOnSpinnerItemSelection() {
        spinnerPoste.setOnItemSelectedListener(new PosteClass());
        spinnerSerie.setOnItemSelectedListener(new SerieClass());
        spinnerSite.setOnItemSelectedListener(new SiteClass());
    }*/

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    public void createDataSite() {

        Groupe group = new Groupe("Site ");
        group.children.add("Paris");
        group.children.add("Toulouse");
        group.children.add("Toulouse");
        groups.append(1, group);

    }

    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        if(longClick) {
            super.onCreateContextMenu(menu, v, menuInfo);
            getMenuInflater().inflate(R.menu.main, menu);
            menu.setHeaderTitle("Que voulez-vous faire ?");
            longClick=false;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.red:
                System.out.println("Modifier");
                break;
            case R.id.green:
                System.out.println("Supprimer");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static void setLongclick(Boolean t){
        longClick=t;
    }
}
