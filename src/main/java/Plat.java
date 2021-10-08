import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Plat implements Serializable {

    private List<Ingredient> ingredientlist;
    private String nom;
    private double prix;

    Plat(String nom, double prix, List<Ingredient> ingredientlist){
        this.nom = nom;
        this.prix = prix;
        this.ingredientlist = ingredientlist;
    }


    public void saveItem() throws IOException {
        FileOutputStream save = new FileOutputStream( "Save\\Plat\\" + this.nom + ".ser"); //CrÃ©er ou remplace le fichier correspondant au chemin
        ObjectOutput oos = new ObjectOutputStream(save); //Permet l'ecriture dans le fichier en paramètre

        oos.writeObject(this); //Sauvegarde sous forme binaire l'objet
    }



    public static Plat loadItem(String name) throws IOException, ClassNotFoundException {
        File save = new File("Save\\Plat\\" + name + ".ser");
        FileInputStream charger = new FileInputStream(save); //RÃ©cupÃ©ration du fichier de sauvegarde
        ObjectInput ois = new ObjectInputStream(charger); //Permet la lecture dans le fichier Ã  charger

        return (Plat) ois.readObject(); //On attribue a la partie actuelle l'objet jeu chargÃ©
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
}
