import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/***
 *
 * Attention :
 *  - Le fichier SAVE correspond en gros à une mini BDD
 *      Comprennant : les différents aliments / plats / Boisson ... Et les clients.
 */
public class Main {
    public static Client clientConnected = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        InitiliazeAllElements();

        ArrayList<String> idClients = getSavesClient();
        Scanner sc = new Scanner(System.in);

        System.out.println("""
                Que voulez vous faire :
                1 : Se connecter
                2 : Créer un id""");

        int connection = Integer.parseInt(sc.nextLine());
        while (connection != 1 && connection != 2){
            connection = Integer.parseInt(sc.nextLine());
        }

        //En sortant du switch le client est obligatoirement connecté
        switch (connection){
            // S'il se connecte
            case 1 -> {
                System.out.print("Indiquer votre id : ");
                String inputId = sc.nextLine();
                //Si l'id n'existe pas alors création de celui-ci
                if(!idClients.contains(inputId)){

                    System.out.println("Id non existant, création de cet id ...");
                    System.out.print("Indiquer votre nom/prénom sout la forme : Nom/Prénom : ");
                    String NameFirstName = sc.nextLine();
                    String[] newClientName = NameFirstName.split("/");
                    clientConnected = new Client(Integer.parseInt(idClients.get(idClients.size() - 1)) + 1, newClientName[0], newClientName[1]);
                }
                //Sinon le connecter
                else {
                    clientConnected = Client.getClientById(Integer.parseInt(inputId));
                }
            }
            //S'il crée un compte Client
            case 2 -> {
                //Récupérer le dernier id et lui donner un nom/prénom
                int newIdClient = Integer.parseInt(idClients.get(idClients.size() - 1)) + 1;
                System.out.print("Votre id sera "+ newIdClient +", indiquer votre nom/prénom sout la forme : Nom/Prénom : ");
                String NameFirstName = sc.nextLine();
                String[] newClientName = NameFirstName.split("/");
                clientConnected = new Client(newIdClient, newClientName[0], newClientName[1]);

            }
        }
        sc.close();

        System.out.println(clientConnected.getNom());

    }


    /***
     * Fonction pour la création des produits, etc, si les fichiers n'ont pas déjà été récupérés
     * Fichier de sauvegarde ->
     *         Save / {Accompagnement}{...}
     */
    public static void InitiliazeAllElements() throws IOException {
        //Raclette
        Ingredient fromageRaclette = new Ingredient("Fromage", 500, 0);
        Ingredient pommeDeTerreRaclette = new Ingredient("Pomme_De_Terre", 200, 10);
        Ingredient charcuterie = new Ingredient("Charcuterie", 300, 0);
        List<Ingredient> racletteList = new ArrayList<>(); racletteList.add(fromageRaclette); racletteList.add(pommeDeTerreRaclette); racletteList.add(charcuterie);
        Plat raclette = new Plat("Raclette", 15, racletteList);

        //Poulet Frite
        Ingredient poulet = new Ingredient("Poulet", 200, 60);
        Ingredient pommeDeTerreFrite = new Ingredient("Pomme_De_Terre", 100, 35);
        List<Ingredient> pouletFriteList = new ArrayList<>(); pouletFriteList.add(poulet);pouletFriteList.add(pommeDeTerreFrite);
        Plat pouletFrite = new Plat("Poulet_Frite", 17.5, pouletFriteList);

        //Coca
        Boisson coca = new Boisson("Coca_Cola",5);

        //Eau
        Boisson eau = new Boisson("Eau", 2);

        //Salade
        Ingredient saladeIngredient = new Ingredient("Salade", 50, 2);
        List<Ingredient> saladeList = new ArrayList<>(); saladeList.add(saladeIngredient);
        Accompagnement salade = new Accompagnement("Salade", 1.6, saladeList);

        //Rizoto
        Ingredient riz = new Ingredient("Riz", 50, 15);
        Ingredient zoto = new Ingredient("Zoto", 50, 10);
        List<Ingredient> rizotoList = new ArrayList<>(); rizotoList.add(riz); rizotoList.add(zoto);
        Accompagnement rizoto = new Accompagnement("Rizoto", 5.6, rizotoList);


        Client cl = new Client(1, "Gilbert", "Montagné");


        raclette.saveItem();
        pouletFrite.saveItem();
        coca.saveItem();
        eau.saveItem();
        salade.saveItem();
        rizoto.saveItem();
        cl.saveItem();

    }

    public static ArrayList<String> getSavesClient(){
        File dossier=new File("Save\\Client\\");
        File[] liste_saves=dossier.listFiles();
        assert liste_saves != null;

        boolean exist = Objects.requireNonNull(liste_saves).length!=0; // Vérfier que le dossier n'est pas vide

        ArrayList<String> liste_string = new ArrayList<>();
        if (exist) {
            int n = 0;
            for (File file : liste_saves) { //Pour chaque sauvegardes présente, on ajoute son nom dans le tableau de retour

                liste_string.add(getRealName(file.getName())); //On récupère le nom des fichiers de sauvegarde
                n++;
            }
        }
        return liste_string; //On retourne la liste contenant les noms de chaques saves au format string
    }

    public static String getRealName(String original_name){
        String file_name = "";
        int i = 0;
        while(original_name.charAt(i) != '.'){
            file_name += original_name.charAt(i);
            i++;
        }
        return file_name;
    }

}
