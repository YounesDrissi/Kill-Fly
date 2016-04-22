package com.abdellah.pcsalon.myapplication.bleutooth;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.abdellah.pcsalon.myapplication.R;

/**
 * Created by Younes on 29/03/2016.
 */
public class Activite2 extends AppCompatActivity {


    private Button button;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testactivite1);
        button=(Button)findViewById(R.id.bouton1);
        button.setOnClickListener(buttonSuivant);

    }

    private View.OnClickListener buttonSuivant=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Activite2.this, Activite1.class);
            startActivity(intent);
        }
    };
}
