package com.abdellah.pcsalon.myapplication.bdd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC SALON on 24/04/2016.
 */
public class GestionBaseDeDonnees {

    //etat de la basse de donnees
    private static boolean existe;
    //choix ==> jouer
    private static String site;
    private static  String poste;
    private static String serie;

    //liste des site,poste,serie
    private List<String> siteList;
    private List<String> posteList;
    private List<String> serieList;


    private List<Site> jouerList;
    private static GestionBaseDeDonnees gestionBaseDeDonnees;

    public GestionBaseDeDonnees() {
        this.jouerList = new ArrayList<Site>();
        this.siteList =new ArrayList<String>();
        this.posteList = new ArrayList<String>();
        this.serieList = new ArrayList<String>();;
    }

    public static GestionBaseDeDonnees getGestionBaseDeDonnees() {
        if(gestionBaseDeDonnees == null){
            gestionBaseDeDonnees = new GestionBaseDeDonnees();
        }
        return gestionBaseDeDonnees;
    }

    public List<Site> getJouerList() {
        return jouerList;
    }

    public List<String> getPosteList() {
        return posteList;
    }

    public List<String> getSerieList() {
        return serieList;
    }

    public static String getSite() {
        return site;
    }

    public static void setSite(String site) {
        GestionBaseDeDonnees.site = site;
    }

    public static String getPoste() {
        return poste;
    }

    public static void setPoste(String poste) {
        GestionBaseDeDonnees.poste = poste;
    }

    public static String getSerie() {
        return serie;
    }

    public static void setSerie(String serie) {
        GestionBaseDeDonnees.serie = serie;
    }

    public List<String> getSiteList() {
        return siteList;
    }

    public void setSiteList(List<String> siteList) {
        this.siteList = siteList;
    }
}
