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
    private Commande commande = new Commande();
    private Client clientConnected = null;
    private Map<Integer, Accompagnement> accompagnementMap = null;
    private Map<Integer, Plat> platMap = null;
    private Map<Integer, Boisson> boissonMap = null;

    private Borne(){}

    public static Borne getInstance() {
        if (borne == null) {
            borne = new Borne();
        }
        return borne;
    }

    // ------------------- Getter -------------------

    public Client getClient(){ return clientConnected; }

    public Commande getCommande() { return commande; }

    public Map<Integer, Accompagnement> getAccompagnementMap() { return accompagnementMap; }

    public Map<Integer, Plat> getPlatMap() { return platMap; }

    public Map<Integer, Boisson> getBoissonMap() { return boissonMap; }

    public void resetCommande() {
        commande = new Commande();
    }
    /*
     * Fonction à exécuter après la connexion d'un client
     */
    public void init() throws IOException, ClassNotFoundException {

        accompagnementMap = getSavesByPathAccompagnement("Save/Accompagnement/");
        platMap = getSavesByPathPlat("Save/Plat/");
        boissonMap = getSavesByPathBoisson("Save/Boisson/");

    }

    public void addPlatToOrder(int key) throws IOException, ClassNotFoundException {
        commande.platList.add(Plat.getPlatByName(platMap.get(key).getNom()));
        commande.calculerPrix();
    }

    public void addAccompagnementToOrder(int key) throws IOException, ClassNotFoundException {
        commande.accompagnementList.add(Accompagnement.getAccompagnementByName(accompagnementMap.get(key).getNom()));
        commande.calculerPrix();
    }

    public void addBoissonToOrder(int key) throws IOException, ClassNotFoundException {
        commande.boissonList.add(Boisson.getBoissonByName(boissonMap.get(key).getNom()));
        commande.calculerPrix();
    }

    public void addMenuToOrder(int plat, int accompagnement, int boisson) throws IOException, ClassNotFoundException {
        Plat platMenu = Plat.getPlatByName(platMap.get(plat).getNom());
        Accompagnement accompagnementMenu = Accompagnement.getAccompagnementByName(accompagnementMap.get(accompagnement).getNom());
        Boisson boissonMenu = Boisson.getBoissonByName(boissonMap.get(boisson).getNom());

        Menu menu = new Menu();

        menu.setPlat(platMenu);
        menu.setAccompagnement(accompagnementMenu);
        menu.setBoisson(boissonMenu);

        commande.menuList.add(menu);
        commande.calculerPrix();
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
    public static Map<Integer, Accompagnement> getSavesByPathAccompagnement(String path) throws IOException, ClassNotFoundException {
        File dossier = new File(path);
        File[] liste_saves = dossier.listFiles();

        assert liste_saves != null;

        boolean exist = Objects.requireNonNull(liste_saves).length != 0;

        Map<Integer, Accompagnement> map = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                map.put(i,Accompagnement.getAccompagnementByName(getRealName(file.getName())));
                i++;
            }
        }
        return map;
    }

    public static Map<Integer, Boisson> getSavesByPathBoisson(String path) throws IOException, ClassNotFoundException {
        File dossier = new File(path);
        File[] liste_saves = dossier.listFiles();

        assert liste_saves != null;

        boolean exist = Objects.requireNonNull(liste_saves).length != 0;

        Map<Integer,Boisson> map = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                map.put(i,Boisson.getBoissonByName(getRealName(file.getName())));
                i++;
            }
        }
        return map;
    }

    public static Map<Integer, Plat> getSavesByPathPlat(String path) throws IOException, ClassNotFoundException {
        File dossier = new File(path);
        File[] liste_saves = dossier.listFiles();

        assert liste_saves != null;

        boolean exist = Objects.requireNonNull(liste_saves).length != 0;

        Map<Integer, Plat> map = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                map.put(i,Plat.getPlatByName(getRealName(file.getName())));
                i++;
            }
        }
        return map;
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
