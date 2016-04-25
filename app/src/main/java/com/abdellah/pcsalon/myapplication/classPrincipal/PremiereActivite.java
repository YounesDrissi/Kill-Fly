package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.abdellah.pcsalon.myapplication.MainActivity;
import com.abdellah.pcsalon.myapplication.R;
import com.abdellah.pcsalon.myapplication.gestionListeSpinner.AndroidSpinnerExampleActivity;

/**
 * Created by Younes on 31/01/2016.
 */
public class PremiereActivite extends AppCompatActivity {


    Button buttonCommence = null;
    Button buttonTest = null;
    Button buttonArchive = null;


    private View.OnClickListener clickListenercOMMENCER = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(PremiereActivite.this, AndroidSpinnerExampleActivity.class);
            startActivity(intent);

        }
    };


    private View.OnClickListener clickListenerTest = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(PremiereActivite.this, MainActivity.class);
            startActivity(intent);

        }
    };

    private View.OnClickListener clickListenerArchive = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent intent = new Intent(PremiereActivite.this, HisoriqueFormulaire.class);
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
