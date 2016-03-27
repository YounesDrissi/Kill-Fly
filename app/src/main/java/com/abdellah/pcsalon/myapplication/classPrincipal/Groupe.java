package com.abdellah.pcsalon.myapplication.classPrincipal;

/**
 * Created by Younes on 16/03/2016.
 */
import java.util.ArrayList;
import java.util.List;

public class Groupe {

    public String string;
    public final List<String> children = new ArrayList<String>();
    public final List<Integer> postes = new ArrayList<Integer>();
    public final List<Integer> series = new ArrayList<Integer>();

    public Groupe(String string) {
        this.string = string;
    }

}
