package com.example.Projet1InsaPOO.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Accompagnement extends Aliment implements Serializable {

    private List<Ingredient> ingredientlist;
    private double prix;
    private boolean onlyMenu;

    public Accompagnement(String nom, double prix, List<Ingredient> ingredientlist,boolean onlyMenu){
        super(nom);
        this.prix = prix;
        this.ingredientlist = ingredientlist;
        this.onlyMenu = onlyMenu;
    }


    public List<Ingredient> getIngredientlist() {
        return ingredientlist;
    }

    public void setIngredientlist(List<Ingredient> ingredientlist) {
        this.ingredientlist = ingredientlist;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public boolean isOnlyMenu() {
        return onlyMenu;
    }

    public void setOnlyMenu(boolean onlyMenu) {
        this.onlyMenu = onlyMenu;
    }


//    public static Accompagnement getAccompagnementByName(String name) throws IOException, ClassNotFoundException {
//        File save = new File("Save/Accompagnement/" + name + ".ser");
//        FileInputStream charger = new FileInputStream(save);
//        ObjectInput ois = new ObjectInputStream(charger);
//
//        return (Accompagnement) ois.readObject();
//    }

}
