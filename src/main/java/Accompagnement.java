import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Accompagnement implements Serializable {

    private List<Ingredient> ingredientlist;
    private String nom;
    private double prix;

    Accompagnement(String nom, double prix, List<Ingredient> ingredientlist){
        this.nom = nom;
        this.prix = prix;
        this.ingredientlist = ingredientlist;
    }

    public List<Ingredient> getIngredientlist() {
        return ingredientlist;
    }

    public void setIngredientlist(List<Ingredient> ingredientlist) {
        this.ingredientlist = ingredientlist;
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
        FileOutputStream save = new FileOutputStream( "Save\\Accompagnement\\" + this.nom + ".ser");
        ObjectOutput oos = new ObjectOutputStream(save);

        oos.writeObject(this);

    }

    public static Accompagnement getAccompagnementByName(String name) throws IOException, ClassNotFoundException {
        File save = new File("Save\\Accompagnement\\" + name + ".ser");
        FileInputStream charger = new FileInputStream(save);
        ObjectInput ois = new ObjectInputStream(charger);

        return (Accompagnement) ois.readObject();
    }
}
