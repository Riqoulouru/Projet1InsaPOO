package com.example.Projet1InsaPOO.Model;

import java.io.*;

public class Boisson extends Aliment implements Serializable{

    private double prix;

    public Boisson(String nom, double prix) {
        super(nom);
        this.prix = prix;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public static Boisson getBoissonByName(String name) throws IOException, ClassNotFoundException {
        File save = new File("Save/Boisson/" + name + ".ser");
        FileInputStream charger = new FileInputStream(save);
        ObjectInput ois = new ObjectInputStream(charger);

        return (Boisson) ois.readObject();
    }


}
