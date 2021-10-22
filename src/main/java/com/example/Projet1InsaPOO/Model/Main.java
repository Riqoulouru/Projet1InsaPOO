package com.example.Projet1InsaPOO.Model;

import java.io.*;
import java.util.*;

/***
 *
 * Attention :
 *  - Le fichier SAVE correspond en gros à une mini BDD
 *      Comprennant : les différents aliments / plats / Boisson ... Et les clients.
 */
public class Main {
    public static Client clientConnected = null;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        InitiliazeAllElements();

        Map<Integer,String> accompagnementMap = getSavesByPath("Save/Accompagnement/");
        Map<Integer,String> platMap = getSavesByPath("Save/Plat/");
        Map<Integer,String> boissonMap = getSavesByPath("Save/Boisson/");
//        accompagnementMap.forEach((r,t) -> System.out.println(r + "   " + t));

        List<Commande> commandesEnCoursDePreparation = new ArrayList<>();
        LinkedList<Commande> commandesEnAttenteDePreparation = new LinkedList<>();

        Cuisine cuisine0 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);
        Cuisine cuisine1 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);
        Cuisine cuisine2 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);

        cuisine0.start();
        cuisine1.start();
        cuisine2.start();

        Commande commande;


        boolean login = false;
        Scanner sc = new Scanner(System.in);

        while (!login){
            login();
            login = true;
            commande = new Commande();

            while (login){
                System.out.print((char)27 + "[33m");
                System.out.println("""
                        Que voulez vous faire :
                        1 : Commander un menu
                        2 : Commander un accompagnement
                        3 : Commander une boisson
                        4 : Commander un plat
                        5 : Payer
                        6 : Modifier commande
                        7 : se déconnecter""");

                int rep;
                try {
                    rep = Integer.parseInt(sc.nextLine());
                } catch (Exception e){
                    rep = 0;
                }


                switch (rep){
                    case 1 -> createMenu(commande,boissonMap,accompagnementMap,platMap);
                    case 2 -> ajouterAccompagnement(accompagnementMap,commande);
                    case 3 -> ajouterBoisson(boissonMap,commande);
                    case 4 -> ajouterPlat(platMap,commande);
                    case 5 -> {
                        commande.calculerTemps();
                        commande.calculerPrix();
                        System.out.println("Le prix de la commande est de " + commande.getPrixTotal());
                        clientConnected.addToHistorique(commande);
                        clientConnected.saveItem();
                        commandesEnAttenteDePreparation.add(commande);
                        login = false;
                        System.out.println("La commande a bien était payer, le temps de préparation de la commande est estimé à " + commande.getTempsCommande() + " minutes");
                        System.out.println("""
                                Vous allez être déconnecté.
                                Bonne appétit""");

                    }
                    case 6 -> System.out.println("modifier commande");
                    case 7 -> login = false;
                    default -> System.out.println("Veuillez selectionner une option valide");
                }

            }
        }
        sc.close();

    }



    public static void createMenu(Commande commande,Map<Integer,String> boissonMap,Map<Integer,String> accompagnementMap,Map<Integer,String> platMap){
        Scanner sc = new Scanner(System.in);
        int rep;
        Menu menu = new Menu();

        System.out.println("Quel plat voulez-vous ajouter à votre menu ? :");
        platMap.forEach((i,s) -> System.out.println(i + " " + s));
        System.out.println(platMap.size() + " annulé le menu");

        try {
            rep = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            rep = 0;
        }

        if (rep == platMap.size()) {
            return;
        } else {
            try {
                menu.setPlat(Plat.getPlatByName(platMap.get(rep)));
                System.out.println(platMap.get(rep) + " ajouté au menu !");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


        System.out.println("Quel accompagnement voulez-vous ajouter à votre menu ? :");
        accompagnementMap.forEach((i,s) -> System.out.println(i + " " + s));
        System.out.println(accompagnementMap.size() + " annulé le menu");

        try {
            rep = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            rep = 0;
        }

        if (rep == accompagnementMap.size()) {
            return;
        } else {
            try {
                menu.setAccompagnement(Accompagnement.getAccompagnementByName(accompagnementMap.get(rep)));
                System.out.println(accompagnementMap.get(rep) + " ajouté au menu !");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Quel boisson voulez-vous ajouter à votre menu ? :");
        boissonMap.forEach((i,s) -> System.out.println(i + " " + s));
        System.out.println(boissonMap.size() + " annulé le menu");

        try {
            rep = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            rep = 0;
        }

        if (rep == boissonMap.size()) {
            return;
        } else {
            try {
                menu.setBoisson(Boisson.getBoissonByName(boissonMap.get(rep)));
                System.out.println(boissonMap.get(rep) + " ajouté au menu !");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }



        commande.getMenuList().add(menu);
        System.out.println("Menu ajouté à la commande !");



    }

    public static void ajouterAccompagnement(Map<Integer,String> map,Commande commande){
        Scanner sc = new Scanner(System.in);
        System.out.println("Quel accompagnement voulez-vous ajouter ? :");
        Map<Integer,String> finalMap = new HashMap<Integer,String>();
        int var = 0;
        for(int i =0; i < map.size(); i++){
            try {
                if(!Accompagnement.getAccompagnementByName(map.get(i)).isOnlyMenu()) {
                    finalMap.put(var, map.get(i));
                    var++;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        finalMap.forEach((i,s) -> System.out.println(i + " " + s));
        System.out.println(finalMap.size() + " retour");
        int rep;
        try {
            rep = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            rep = 0;
        }

        if (rep != finalMap.size()) {
            try {
                commande.accompagnementList.add(Accompagnement.getAccompagnementByName(finalMap.get(rep)));
                System.out.println(finalMap.get(rep) + " ajouter à la commande");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    public static void ajouterPlat(Map<Integer,String> map,Commande commande){
        Scanner sc = new Scanner(System.in);
        System.out.println("Quel plat voulez-vous ajouter ? :");
        Map<Integer,String> finalMap = new HashMap<Integer,String>();
        int var = 0;
        for(int i =0; i < map.size(); i++){
            try {
                if(!Plat.getPlatByName(map.get(i)).isOnlyMenu()) {
                    finalMap.put(var, map.get(i));
                    var++;
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        finalMap.forEach((i,s) -> System.out.println(i + " " + s));
        System.out.println(finalMap.size() + " retour");
        int rep;
        try {
            rep = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            rep = 0;
        }

        if (rep == map.size()) {
            return;
        } else {
            try {
                commande.platList.add(Plat.getPlatByName(finalMap.get(rep)));
                System.out.println(finalMap.get(rep) + " ajouter à la commande");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


    }

    public static void ajouterBoisson(Map<Integer, String> map,Commande commande) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Que boisson voulez-vous ajouter ? :");
        map.forEach((i, s) -> System.out.println(i + " " + s));
        System.out.println(map.size() + " retour");
        int rep;
        try {
            rep = Integer.parseInt(sc.nextLine());
        } catch (Exception e) {
            rep = 0;
        }

        if (rep == map.size()) {
            return;
        } else {
            try {
                commande.boissonList.add(Boisson.getBoissonByName(map.get(rep)));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
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


    public static String getRealName(String original_name){
        String file_name = "";
        int i = 0;
        while(original_name.charAt(i) != '.'){
            file_name += original_name.charAt(i);
            i++;
        }
        return file_name;
    }

    /***
     * Fonction pour la création des produits, etc, si les fichiers n'ont pas déjà été récupérés
     * Fichier de sauvegarde ->
     *         Save / {Accompagnement}{...}
     */
    public static void InitiliazeAllElements() throws IOException {
        //Raclette
        Ingredient fromageRaclette = new Ingredient("Fromage-raclette", 500, 0);
        Ingredient pommeDeTerreRaclette = new Ingredient("Pomme_De_Terre", 200, 10);
        Ingredient charcuterie = new Ingredient("Charcuterie", 300, 0);
        List<Ingredient> racletteList = new ArrayList<>(); racletteList.add(fromageRaclette); racletteList.add(pommeDeTerreRaclette); racletteList.add(charcuterie);
        Plat raclette = new Plat("Raclette", 15, racletteList,false);

        //Poulet Frite
        Ingredient poulet = new Ingredient("Poulet", 200, 60);
        Ingredient pommeDeTerreFrite = new Ingredient("Pomme_De_Terre", 100, 35);
        List<Ingredient> pouletFriteList = new ArrayList<>(); pouletFriteList.add(poulet);pouletFriteList.add(pommeDeTerreFrite);
        Plat pouletFrite = new Plat("Poulet_Frite", 17.5, pouletFriteList,false);

        //Burger
        Ingredient fromage = new Ingredient("Fromage", 500, 0);
        Ingredient steak = new Ingredient("Steak", 300, 10);
        Ingredient tomate = new Ingredient("Tomate", 300, 0);
        Ingredient saladeBurger = new Ingredient("Salade", 300, 0);

        List<Ingredient> burgerList = new ArrayList<>(); burgerList.add(fromage); burgerList.add(steak); burgerList.add(tomate); burgerList.add(saladeBurger);
        Plat burger = new Plat("Burger", 7, burgerList,true);

        //Coca
        Boisson coca = new Boisson("Coca_Cola",5);

        //Eau
        Boisson eau = new Boisson("Eau", 2);

        //Salade
        Ingredient saladeIngredient = new Ingredient("Salade", 50, 2);
        List<Ingredient> saladeList = new ArrayList<>(); saladeList.add(saladeIngredient);
        Accompagnement salade = new Accompagnement("Salade", 1.6, saladeList,false);

        //Rizoto
        Ingredient riz = new Ingredient("Riz", 50, 15);
        Ingredient zoto = new Ingredient("Zoto", 50, 10);
        List<Ingredient> rizotoList = new ArrayList<>(); rizotoList.add(riz); rizotoList.add(zoto);
        Accompagnement rizoto = new Accompagnement("Rizoto", 5.6, rizotoList,false);

        //Frites
        Ingredient patates = new Ingredient("Patates", 100, 15);
        Ingredient sel = new Ingredient("Sel", 10, 0);
        List<Ingredient> fritesList = new ArrayList<>(); rizotoList.add(patates); rizotoList.add(sel);
        Accompagnement frites = new Accompagnement("Frites", 2.5, fritesList,true);

        Client cl = new Client(1, "Gilbert", "Montagné");


        raclette.saveItem();
        pouletFrite.saveItem();
        coca.saveItem();
        eau.saveItem();
        salade.saveItem();
        rizoto.saveItem();
        cl.saveItem();
        burger.saveItem();
        frites.saveItem();


    }

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
                    login = true;
                }
            }
        }
        System.out.print((char)27 + "[33m");
        System.out.println("""
                ----------------------------
                
                Bonjour\040""" + clientConnected.getNom() + " (～￣▽￣)～");
    }
}
