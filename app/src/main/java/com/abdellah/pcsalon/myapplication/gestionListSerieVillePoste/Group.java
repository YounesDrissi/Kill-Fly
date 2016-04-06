package com.abdellah.pcsalon.myapplication.gestionListSerieVillePoste;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC SALON on 29/03/2016.
 */
public class Group {
    public String string;
    public final List<String> children = new ArrayList<String>();
    //public final List<Integer> serie = new ArrayList<>();

    public Group(String string) {
        this.string = string;
    }

}
