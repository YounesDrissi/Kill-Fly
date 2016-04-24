package com.abdellah.pcsalon.myapplication.bdd;

import java.util.Date;

/**
 * Created by PC SALON on 24/04/2016.
 */
class HistoriqueVille {

    private Site ville;
    private String poste;
    private String serie;
    private int[][] infos;
    private Date date;

    public HistoriqueVille(Site ville, String serie, String poste, Date date, int[][] infos) {
        this.ville = ville;
        this.serie = serie;
        this.poste = poste;
        this.date = date;
        this.infos = infos;
    }

    public Site getVille() {
        return ville;
    }

    public String getPoste() {
        return poste;
    }

    public String getSerie() {
        return serie;
    }

    public int[][] getInfos() {
        return infos;
    }

    public Date getDate() {
        return date;
    }
}
