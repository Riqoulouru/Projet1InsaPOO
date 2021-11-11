package com.example.Projet1InsaPOO.Model;

import java.io.*;

public abstract class Aliment implements Serializable,Produit {

    private final String nom;
    private double prix;

    public Aliment(String nom, Double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void saveItem(String path) throws IOException {
        FileOutputStream save = new FileOutputStream( path + getNom() + ".ser");
        ObjectOutput oos = new ObjectOutputStream(save);
        oos.writeObject(this);
    }

    public static Aliment getAlimentByName(String path,String name) throws IOException, ClassNotFoundException {
        File save = new File(path + name + ".ser");
        FileInputStream charger = new FileInputStream(save);
        ObjectInput ois = new ObjectInputStream(charger);

        return (Aliment) ois.readObject();
    }

    @Override
    public String getAffichageProduit() {
        return getNom();
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }
}
