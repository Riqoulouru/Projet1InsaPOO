package com.example.Projet1InsaPOO.Model;

import com.example.Projet1InsaPOO.Projet1InsaPooApplication;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * La classe borne correspond à la borne sur laquelle le client commande
 * -> Elle est l'équivalent du Main s'il n'y avait pas d'interface graphique
 * -> Elle transmet ses données traitées aux controllers qui eux enverront ces données aux pages pour un simple affichage
 * (Les seuls traitements en js sont les requêtes AJAX pour send/get les données et les traitements pour l'affichage)
 * Borne est un singleton, afin que tous les controllers puissent avoir la même "Borne"
 * Ainsi transmettre les données aux pages facilement
 */
public class Borne {

    private static Borne borne; // Singleton
    private Commande commande; // -> La commande actuelle sur la borne
    private Client clientConnected = null; // -> Le client connecté
    private Map<Integer, Accompagnement> accompagnementMap = null; // -> Tous les accompagnements dans la "BDD"
    private Map<Integer, Plat> platMenuMap = null; // -> Tous les plats dans la "BDD"
    private Map<Integer, Plat> platMap = null; // -> Les plats dans la "BDD" qui peuvent être compris hors menu
    private Map<Integer, Boisson> boissonMap = null; // -> Toutes les boissons dans la "BDD"

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

    public Map<Integer, Plat> getPlatMenuMap() { return platMenuMap; }

    public Map<Integer, Plat> getPlatMap() { return platMap; }

    public Map<Integer, Boisson> getBoissonMap() { return boissonMap; }

    /*
     * Méthode pour reset la commande quand on est à la page login
     */
    public void resetCommande() { commande = new Commande(); }

    /*
     * Fonction à exécuter après la connexion d'un client -> Récupération des éléments commandables
     */
    public void init() throws IOException, ClassNotFoundException {

        accompagnementMap = getSavesByPathAccompagnement("Save/Accompagnement/");
        platMenuMap = getSavesByPathPlat("Save/Plat/");
        boissonMap = getSavesByPathBoisson("Save/Boisson/");

        // Initialiser platMap
        platMap = new HashMap<>();
        int key = 0;
        for(Plat plat : platMenuMap.values()) {
            if (!plat.isOnlyMenu()) {
                platMap.put(key, plat);
            }
            key++;
        }

    }

    // --------------- Ajout d'éléments dans la commande ---------------
    /*
     * 1 : Ajouter l'éléments dans la commande
     * 2 : Mettre à jour le prix de la commande
     */
    public void addPlatToOrder(int key) throws IOException, ClassNotFoundException {
        commande.platList.add((Plat) Plat.getAlimentByName("Save/Plat/",platMap.get(key).getNom()));
        commande.calculerPrix();
    }

    public void addAccompagnementToOrder(int key) throws IOException, ClassNotFoundException {
        commande.accompagnementList.add((Accompagnement) Accompagnement.getAlimentByName("Save/Accompagnement/",accompagnementMap.get(key).getNom()));
        commande.calculerPrix();
    }

    public void addBoissonToOrder(int key) throws IOException, ClassNotFoundException {
        commande.boissonList.add((Boisson) Boisson.getAlimentByName("Save/Boisson/",boissonMap.get(key).getNom()));
        commande.calculerPrix();
    }

    public void addMenuToOrder(int plat, int accompagnement, int boisson) throws IOException, ClassNotFoundException {
        Plat platMenu = (Plat) Plat.getAlimentByName("Save/Plat/",platMenuMap.get(plat).getNom());
        Accompagnement accompagnementMenu = (Accompagnement) Accompagnement.getAlimentByName("Save/Accompagnement/",accompagnementMap.get(accompagnement).getNom());
        Boisson boissonMenu = (Boisson) Boisson.getAlimentByName("Save/Boisson/",boissonMap.get(boisson).getNom());

        Menu menu = new Menu();

        menu.setPlat(platMenu);
        menu.setAccompagnement(accompagnementMenu);
        menu.setBoisson(boissonMenu);

        commande.menuList.add(menu);
        commande.calculerPrix();
    }

    /*
     * Traitement à appliquer quand le client paye sa commande
     */
    public String payer() throws IOException {
        commande.calculerTemps();
        commande.calculerPrix();

        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        commande.setDateValidation(myDateObj.format(myFormatObj));

        clientConnected.addToHistorique(commande);
        clientConnected.saveItem();

        // Après une nouvelle commande, réveiller les cuisines qu'une commande est à préparer
        synchronized (Projet1InsaPooApplication.cuisines.get(0).getCommandesEnAttenteDePreparation()){
            Projet1InsaPooApplication.cuisines.get(0).addCommandesEnAttenteDePreparation(commande);
            Projet1InsaPooApplication.cuisines.get(0).getCommandesEnAttenteDePreparation().notifyAll();
        }


        /*
         * TODO : Traitement avec cuisine
         *  commandesEnAttenteDePreparation.add(commande);
         */

        return "Payement de "+ commande.getPrixTotal() +"€ accepté, votre commande a bien été prise en compte. <br>" +
                "Attente estimé : "+ commande.getTempsCommande() + "minutes <br><br>" +
                "Sur la page de connexion cliquez sur avancement pour voir l'avancée de voter commande " + "<br>" +
                "Appuyer sur confirmer pour être déconnecté. <br>";
    }


    /*
     *Traitement pour modifier la commande
     *  (Supprimer un élément)
     */
    public String removeElement(int index, String type){

        switch (type) {
            case "plat" -> commande.platList.remove(index);
            case "boisson" -> commande.boissonList.remove(index);
            case "accompagnement" -> commande.accompagnementList.remove(index);
            case "menu" -> commande.menuList.remove(index);
        }
        commande.calculerPrix();

        return "done";
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
     * Traitement après l'inscription d'un client
     */
    public String inscription(String nom, String prenom) throws IOException {
        //Récupération du dernière id + 1
        ArrayList<String> idClients = getSavesClient();
        int newIdClient = Integer.parseInt(idClients.get(idClients.size() - 1)) + 1;

        //Création du client et sauvegarde
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
        //Récupération des idClients
        ArrayList<String> idClients = getSavesClient();
        String newId = String.valueOf(id);
        //S'il n'existe pas
        if (!idClients.contains(newId)) {
            return "L'identifiant n'existe pas, veuillez vous inscrire";
        }
        //S'il existe
        else {
            try {
                // Indiquer le client connecté sur la borne actuellement
                clientConnected = Client.getClientById(Integer.parseInt(newId));
                return "Validate";
                //return "Bonjour " + clientConnected.getNom() + " (～￣▽￣)～";
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return "error";
    }

    // --------------- Récupération des éléments ---------------


    public static Map<Integer, Accompagnement> getSavesByPathAccompagnement(String path) throws IOException, ClassNotFoundException {
        //Récupérer les fichiers du dossier
        File dossier = new File(path);
        File[] liste_saves = dossier.listFiles();

        assert liste_saves != null;
        boolean exist = Objects.requireNonNull(liste_saves).length != 0;

        //Ajout des éléments dans la Map
        Map<Integer, Accompagnement> map = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                map.put(i,(Accompagnement) Accompagnement.getAlimentByName("Save/Accompagnement/",getRealName(file.getName())));
                i++;
            }
        }
        return map;
    }

    public static Map<Integer, Boisson> getSavesByPathBoisson(String path) throws IOException, ClassNotFoundException {
        //Récupérer les fichiers du dossier
        File dossier = new File(path);
        File[] liste_saves = dossier.listFiles();

        assert liste_saves != null;
        boolean exist = Objects.requireNonNull(liste_saves).length != 0;

        //Ajout des éléments dans la Map
        Map<Integer,Boisson> map = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                map.put(i,(Boisson) Boisson.getAlimentByName("Save/Boisson/",getRealName(file.getName())));
                i++;
            }
        }
        return map;
    }

    public static Map<Integer, Plat> getSavesByPathPlat(String path) throws IOException, ClassNotFoundException {
        //Récupérer les fichiers du dossier
        File dossier = new File(path);
        File[] liste_saves = dossier.listFiles();

        assert liste_saves != null;
        boolean exist = Objects.requireNonNull(liste_saves).length != 0;

        //Ajout des éléments dans la Map
        Map<Integer, Plat> map = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                map.put(i,(Plat) Plat.getAlimentByName("Save/Plat/",getRealName(file.getName())));
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
