package com.abdellah.pcsalon.myapplication.classPrincipal;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.abdellah.pcsalon.myapplication.R;

/**
 * Created by Younes on 16/03/2016.
 */
public class MyList implements AdapterView.OnItemLongClickListener{


    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

        System.out.println("lololololol");
        Toast.makeText(parent.getContext(),"Serie "+
                        parent.getItemAtPosition(position).toString()+" Selectionnée",
                Toast.LENGTH_SHORT).show();
        return false;
    }
}
