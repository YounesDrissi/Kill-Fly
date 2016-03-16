package com.abdellah.pcsalon.myapplication.classPrincipal;

/**
 * Created by Younes on 16/03/2016.
 */
import java.util.ArrayList;
import java.util.List;
public class Group {

    public String string;
    public final List<String> children = new ArrayList<String>();

    public Group(String string) {
        this.string = string;
    }

}
