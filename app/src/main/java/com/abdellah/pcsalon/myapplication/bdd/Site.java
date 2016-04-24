package com.abdellah.pcsalon.myapplication.bdd;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by PC SALON on 24/04/2016.
 */
class Site {
    private String nomLieux;
    private List<HistoriqueVille> historiqueVilles;

    public Site(String nomLieux) {
        this.nomLieux = nomLieux;
        this.historiqueVilles = new ArrayList<HistoriqueVille>();
    }
    public void addHistorique(HistoriqueVille h){
        this.historiqueVilles.add(h);
    }
    public List<HistoriqueVille> getHistorique(){
        return (historiqueVilles);
    }
}