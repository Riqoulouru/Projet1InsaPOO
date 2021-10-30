package com.example.Projet1InsaPOO.Model;

import java.io.*;
import java.text.SimpleDateFormat;

public class Ingredient implements Serializable,Produit{

    private int quantite;
    private double tempsCuisson;
    private final String nom;

    public Ingredient(String nom, int quantite, double tempsCuisson){
        this.nom = nom;
        this.quantite = quantite;
        this.tempsCuisson = tempsCuisson;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getTempsCuisson() {
        return tempsCuisson;
    }

    public void setTempsCuisson(double tempsCuisson) {
        this.tempsCuisson = tempsCuisson;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String getAffichageProduit() {
        return nom;
    }
}
