package com.abdellah.pcsalon.myapplication.gestionListSerieVillePoste;


import android.app.Activity;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.ExpandableListView;

import com.abdellah.pcsalon.myapplication.R;

/**
 * Created by PC SALON on 29/03/2016.
 */

public class ListActivity extends Activity {
    // more efficient than HashMap for mapping integers to objects
    SparseArray<Group> groups = new SparseArray<Group>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_lieu_poste_serie);
        createData();
        ExpandableListView listView = (ExpandableListView) findViewById(R.id.listView);
        MyExpandableListAdapter adapter = new MyExpandableListAdapter(this,
                groups);
        //listView.setAdapter(adapter);
        if(adapter==null){
            System.out.println("NOT NULL");
        }

        listView.setAdapter(adapter);
    }

    public void createData() {
       /* for (int j = 0; j < 3; j++) {
            Group group = new Group("Test " + j);
            for (int i = 0; i < 5; i++) {
                group.children.add("Sub Item" + i);
            }
            groups.append(j, group);
        }*/
        //creation group site
        Group site=new Group("Site");
        site.children.add("Toulouse");
        site.children.add("Paris");
        site.children.add("Monptelier");
        groups.append(0, site);
        //creation group série
        Group serie=new Group("Série");
        serie.children.add("0");
        serie.children.add("1");
        serie.children.add("2");
        serie.children.add("3");
        groups.append(1, serie);
        //creation group poste
        Group poste=new Group("Poste");
        poste.children.add("5");
        poste.children.add("6");
        poste.children.add("7");
        poste.children.add("8");
        groups.append(2, poste);

    }

}

