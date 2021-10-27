package com.example.Projet1InsaPOO.Model;

import java.io.*;

public abstract class Aliment implements Serializable {

    private final String nom;

    public Aliment(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void saveItem(String path) throws IOException {
        FileOutputStream save = new FileOutputStream( path + getNom() + ".ser");
        ObjectOutput oos = new ObjectOutputStream(save);
        oos.writeObject(this);
    }

    @Override
    public String toString() {
        return nom;
    }
}
