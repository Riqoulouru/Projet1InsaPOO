package com.example.Projet1InsaPOO.Model;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Plat extends Aliment implements Serializable {

    private List<Ingredient> ingredientlist;
    private boolean onlyMenu;

    public Plat(String nom, double prix, List<Ingredient> ingredientlist, boolean onlyMenu){
        super(nom, prix);
        this.ingredientlist = ingredientlist;
        this.onlyMenu = onlyMenu;
    }


    public List<Ingredient> getIngredientlist() {
        return ingredientlist;
    }

    public void setIngredientlist(List<Ingredient> ingredientlist) {
        this.ingredientlist = ingredientlist;
    }

    public boolean isOnlyMenu() {
        return onlyMenu;
    }

    public void setOnlyMenu(boolean onlyMenu) {
        this.onlyMenu = onlyMenu;
    }

//    public static Plat getPlatByName(String name) throws IOException, ClassNotFoundException {
//        File save = new File("Save/Plat/" + name + ".ser");
//        FileInputStream charger = new FileInputStream(save);
//        ObjectInput ois = new ObjectInputStream(charger);
//
//        return (Plat) ois.readObject();
//    }
}
