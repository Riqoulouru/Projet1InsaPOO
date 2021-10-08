import java.io.*;

public class Boisson {

    private String nom;
    private double prix;


    public void saveItem() throws IOException {
        FileOutputStream save = new FileOutputStream( "Save\\Boisson\\" + this.nom + ".ser"); //CrÃ©er ou remplace le fichier correspondant au chemin
        ObjectOutput oos = new ObjectOutputStream(save); //Permet l'ecriture dans le fichier en paramÃ¨tre

        oos.writeObject(this); //Sauvegarde sous forme binaire l'objet (ici il s'agit de 'jeu')

    }



    public static Boisson loadItem(File save) throws IOException, ClassNotFoundException {
        FileInputStream charger = new FileInputStream(save); //RÃ©cupÃ©ration du fichier de sauvegarde
        ObjectInput ois = new ObjectInputStream(charger); //Permet la lecture dans le fichier Ã  charger

        return (Boisson) ois.readObject(); //On attribue a la partie actuelle l'objet jeu chargÃ©
    }

}
