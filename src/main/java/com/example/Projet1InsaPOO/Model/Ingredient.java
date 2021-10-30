package com.example.Projet1InsaPOO.Model;

import java.io.*;
import java.text.SimpleDateFormat;

public class Ingredient extends Aliment implements Serializable{

    private int quantite;
    private double tempsCuisson;

    public Ingredient(String nom, int quantite, double tempsCuisson){
        super(nom);
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

    public String toString(){
        return getNom() + " ";
    }

}
