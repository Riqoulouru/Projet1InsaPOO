import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Accompagnement {

    private List<Ingredient> ingredientlist;
    private String nom;
    private double prix;

    public List<Ingredient> getIngredientlist() {
        return ingredientlist;
    }
    public void saveItem() throws IOException {
        FileOutputStream save = new FileOutputStream( "Save\\Accompagnement\\" + this.nom + ".ser"); //CrÃ©er ou remplace le fichier correspondant au chemin
        ObjectOutput oos = new ObjectOutputStream(save); //Permet l'ecriture dans le fichier en paramÃ¨tre

        oos.writeObject(this); //Sauvegarde sous forme binaire l'objet (ici il s'agit de 'jeu')

    }


    public static Accompagnement loadItem(File save) throws IOException, ClassNotFoundException {
        FileInputStream charger = new FileInputStream(save); //RÃ©cupÃ©ration du fichier de sauvegarde
        ObjectInput ois = new ObjectInputStream(charger); //Permet la lecture dans le fichier Ã  charger

        return (Accompagnement) ois.readObject(); //On attribue a la partie actuelle l'objet jeu chargÃ©
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
