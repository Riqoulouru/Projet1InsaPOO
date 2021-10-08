import java.io.*;
import java.text.SimpleDateFormat;

public class Ingredient implements Serializable{


    private String nom;
    private int quantite;
    private double tempsCuisson;


    Ingredient(String nom, int quantite, double tempsCuisson){
        this.nom = nom;
        this.quantite = quantite;
        this.tempsCuisson = tempsCuisson;
    }


    public Ingredient(String nom, double tempsCuisson) {
        this.nom = nom;
        this.tempsCuisson = tempsCuisson;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
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
        return nom + " ";
    }
}
