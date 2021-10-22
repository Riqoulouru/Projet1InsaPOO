package com.example.Projet1InsaPOO.Model;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Borne est un singleton, afin que tous les controllers puissent avoir la même "Borne"
 * Ainsi transmettre les données aux pages facilement
 */
public class Borne {

    private static Borne borne;
    private Client clientConnected = null;
    private Map<Integer, String> accompagnementMap = null;
    private Map<Integer, String> platMap = null;
    private Map<Integer, String> boissonMap = null;

    private Borne(){}

    public static Borne getInstance() {
        if (borne == null) {
            borne = new Borne();
        }
        return borne;
    }

    // ------------------- Getter -------------------

    public Client getClient(){
        return clientConnected;
    }

    public Map<Integer, String> getAccompagnementMap() {
        return accompagnementMap;
    }

    public Map<Integer, String> getPlatMap() {
        return platMap;
    }

    public Map<Integer, String> getBoissonMap() {
        return boissonMap;
    }


    /*
     * Fonction à exécuter après la connexion d'un client
     */
    public void init() {

        accompagnementMap = getSavesByPath("Save/Accompagnement/");
        platMap = getSavesByPath("Save/Plat/");
        boissonMap = getSavesByPath("Save/Boisson/");

    }

    //Récupérer la liste des identifiants des clients
    public static ArrayList<String> getSavesClient(){
        File dossier=new File("Save/Client/");
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


    /*
    Traitement pour se connecter (mode console)
    public static void login() throws IOException, ClassNotFoundException {
        System.out.print((char)27 + "[32m");

        ArrayList<String> idClients = getSavesClient();
        Scanner sc = new Scanner(System.in);
        boolean login = false;

        while(!login) {
            System.out.println("""
                    
                    Que voulez vous faire :
                    1 : Se connecter
                    2 : Créer un id""");

            int connection = Integer.parseInt(sc.nextLine());
            while (connection != 1 && connection != 2) {
                System.out.println("Veuillez indiquer une réponse valable.");
                connection = Integer.parseInt(sc.nextLine());
            }

            //En sortant du switch le client est obligatoirement connecté
            switch (connection) {
                // S'il se connecte
                case 1 -> {
                    System.out.print("Indiquer votre id : ");
                    String inputId = sc.nextLine();
                    //Si l'id n'existe alors recommencer la boucle
                    if (!idClients.contains(inputId)) {
                        System.out.println(inputId + " non existant");
                    }
                    //Sinon le connecter
                    else {
                        clientConnected = Client.getClientById(Integer.parseInt(inputId));
                        login = true;
                    }
                }
                //S'il crée un compte Client
                case 2 -> {
                    //Récupérer le dernier id et lui donner un nom/prénom
                    int newIdClient = Integer.parseInt(idClients.get(idClients.size() - 1)) + 1;
                    System.out.print("Votre id sera " + newIdClient + ", indiquer votre nom/prénom sout la forme : Nom/Prénom : ");
                    String NameFirstName = sc.nextLine();
                    String[] newClientName = NameFirstName.split("/");
                    clientConnected = new Client(newIdClient, newClientName[0], newClientName[1]);

                }
            }
        }
        System.out.print((char)27 + "[33m");
        System.out.println("""
                ----------------------------
                
                Bonjour\040""" + clientConnected.getNom() + " (～￣▽￣)～");
    }
    */

    /*
     * Traitement après l'inscription d'un client
     */
    public String inscription(String nom, String prenom) throws IOException {

        ArrayList<String> idClients = getSavesClient();
        int newIdClient = Integer.parseInt(idClients.get(idClients.size() - 1)) + 1;

        clientConnected = new Client(newIdClient, nom, prenom);
        clientConnected.saveItem();

        return "Bonjour " + prenom + ", votre id sera : " + newIdClient +", veuillez vous connecter.";
    }

    /**
     * Traitement pour la connection du client
     *  -> S'il n'est pas dans la liste alors il n'existe pas
     *  -> Autrement retourner Validate pour rediriger vers la page suivante
     */
    public String login(int id){

        ArrayList<String> idClients = getSavesClient();
        String newId = String.valueOf(id);
        if (!idClients.contains(newId)) {
            return "L'identifiant n'existe pas, veuillez vous inscrire";
        } else {
            try {
                clientConnected = Client.getClientById(Integer.parseInt(newId));
                return "Validate";
                //return "Bonjour " + clientConnected.getNom() + " (～￣▽￣)～";
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return "error";
    }

    /*
     * Fonction pour récupérer les différents éléments en fonction du type d'éléments voulus
     */
    public static Map<Integer,String> getSavesByPath(String path){
        File dossier = new File(path);
        File[] liste_saves = dossier.listFiles();

        assert liste_saves != null;

        boolean exist = Objects.requireNonNull(liste_saves).length != 0;

        Map<Integer,String> accompagnementMap = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                accompagnementMap.put(i,getRealName(file.getName()));
                i++;
            }
        }
        return accompagnementMap;
    }


    /*
     * Fonction pour récupérer les noms du fichier sans l'extension (.ser)
     */
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
