public class Ingredient {


    private String nom;
    private int quantite;
    private double tempsCuisson;


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
}
