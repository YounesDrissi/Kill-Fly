package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

/**
 * Created by PC SALON on 19/02/2016.
 */
public class HistoriqueClass implements AdapterView.OnItemSelectedListener {


    public Context contex;
    public String ville;

    public HistoriqueClass (Context c){
        this.contex=c;
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        ArrayAdapter<String> adapter;
        String[][] values = new String[][]{{"14/12/1990","14/12/1991","14/12/1993","14/12/1994","12/11/2014", "12/11/2015", "12/11/2016"},
                                             {"12/11/2011", "12/11/2012", "12/11/2016","12/11/2011", "12/11/2012", "12/11/2016"},
                                                {"12/11/2014", "12/11/2015", "12/11/2016","12/11/2014", "12/11/2015", "12/11/2016"},
                                                   {"12/11/2014", "12/11/2015", "12/11/2016"}};

       ville=parent.getItemAtPosition(pos).toString();


        switch (pos) {
            case 0:

                adapter = new ArrayAdapter<String>(contex,
                        android.R.layout.simple_list_item_1, values[0]);

                HisoriqueFormulaire.listHistorique.setAdapter(adapter);
                break;
            case 1:

                adapter = new ArrayAdapter<String>(contex,
                        android.R.layout.simple_list_item_1, values[1]);

                HisoriqueFormulaire.listHistorique.setAdapter(adapter);
                break;
            case 3:

                 adapter = new ArrayAdapter<String>(contex,
                        android.R.layout.simple_list_item_1, values[2]);

                HisoriqueFormulaire.listHistorique.setAdapter(adapter);
                break;
            case 4:

                 adapter = new ArrayAdapter<String>(contex,
                        android.R.layout.simple_list_item_1, values[3]);

                HisoriqueFormulaire.listHistorique.setAdapter(adapter);
                break;
            default :
                adapter = new ArrayAdapter<String>(contex,
                        android.R.layout.simple_list_item_1,new String[]{});

                HisoriqueFormulaire.listHistorique.setAdapter(adapter);
        }

        HisoriqueFormulaire.listHistorique.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(parent.getContext(),
                        "ville : "+ville+"date : "+parent.getItemAtPosition(position).toString() ,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
   
    }

}