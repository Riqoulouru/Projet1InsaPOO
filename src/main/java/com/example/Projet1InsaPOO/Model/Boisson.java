package com.example.Projet1InsaPOO.Model;

import java.io.*;

public class Boisson implements Serializable {

    private String nom;
    private double prix;

    Boisson(String nom, double prix) {
        this.nom = nom;
        this.prix = prix;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public void saveItem() throws IOException {
        FileOutputStream save = new FileOutputStream("Save/Boisson/" + this.nom + ".ser");
        ObjectOutput oos = new ObjectOutputStream(save);

        oos.writeObject(this);

    }

    public static Boisson getBoissonByName(String name) throws IOException, ClassNotFoundException {
        File save = new File("Save/Boisson/" + name + ".ser");
        FileInputStream charger = new FileInputStream(save);
        ObjectInput ois = new ObjectInputStream(charger);

        return (Boisson) ois.readObject();
    }


}
