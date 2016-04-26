package com.abdellah.pcsalon.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.abdellah.pcsalon.myapplication.gestionListeSpinner.AndroidSpinnerExampleActivity;
import com.abdellah.pcsalon.myapplication.historique.HisoriqueFormulaire;

/**
 * Created by Younes on 31/01/2016.
 */
public class MainActivity extends AppCompatActivity {


    private Button buttonCommence = null;
    private Button buttonTest = null;
    private Button buttonArchive = null;
    private final String EXTRA_MAIN="mainActivitePassage";


    private View.OnClickListener clickListenercOMMENCER = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, AndroidSpinnerExampleActivity.class);
            startActivity(intent);

        }
    };


    private View.OnClickListener clickListenerTest = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, Fragments.class);
            intent.putExtra(EXTRA_MAIN, 1);
            startActivity(intent);

        }
    };

    private View.OnClickListener clickListenerArchive = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(MainActivity.this, HisoriqueFormulaire.class);
            startActivity(intent);

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);


        buttonCommence = (Button) findViewById(R.id.buttonEntrer);
        buttonTest = (Button) findViewById(R.id.buttonTest);
        buttonArchive = (Button) findViewById(R.id.buttonHistorique);

        buttonCommence.setOnClickListener(clickListenercOMMENCER);
        buttonTest.setOnClickListener(clickListenerTest);
        buttonArchive.setOnClickListener(clickListenerArchive);
    }
}
