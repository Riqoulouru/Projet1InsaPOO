package com.example.Projet1InsaPOO.Model;

import java.io.*;

public class Boisson extends Aliment implements Serializable{

    public Boisson(String nom, double prix) {
        super(nom, prix);
    }

//    public static Boisson getBoissonByName(String name) throws IOException, ClassNotFoundException {
//        File save = new File("Save/Boisson/" + name + ".ser");
//        FileInputStream charger = new FileInputStream(save);
//        ObjectInput ois = new ObjectInputStream(charger);
//
//        return (Boisson) ois.readObject();
//    }


}
