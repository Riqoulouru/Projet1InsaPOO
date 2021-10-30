package com.example.Projet1InsaPOO.Model;

import java.io.Serializable;

public class Menu implements Serializable,Produit {

    private Plat plat;
    private Boisson boisson;
    private Accompagnement accompagnement;


    public Plat getPlat() {
        return plat;
    }

    public void setPlat(Plat plat) {
        this.plat = plat;
    }

    public Boisson getBoisson() {
        return boisson;
    }

    public void setBoisson(Boisson boisson) {
        this.boisson = boisson;
    }

    public Accompagnement getAccompagnement() {
        return accompagnement;
    }

    public void setAccompagnement(Accompagnement accompagnement) {
        this.accompagnement = accompagnement;
    }

    @Override
    public String getAffichageProduit() {
        return "Menu : " +
                " plat : " + plat.getNom() +
                " boisson : " + boisson.getNom() +
                " accompagnement : " + accompagnement.getNom();
    }
}
