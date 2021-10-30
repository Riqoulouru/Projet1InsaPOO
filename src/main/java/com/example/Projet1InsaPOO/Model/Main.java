package com.example.Projet1InsaPOO.Model;

import com.example.Projet1InsaPOO.Projet1InsaPooApplication;

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
//        Projet1InsaPooApplication.initiliazeAllElements();

        Map<Integer,String> accompagnementMap = getSavesByPath("Save/Accompagnement/");
        Map<Integer,String> platMap = getSavesByPath("Save/Plat/");
        Map<Integer,String> boissonMap = getSavesByPath("Save/Boisson/");

        final List<Commande> commandesEnCoursDePreparation = new ArrayList<>();
        final LinkedList<Commande> commandesEnAttenteDePreparation = new LinkedList<>();

        List<Cuisine> cuisineList = new ArrayList<>();

        Cuisine cuisine0 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation,"une");
        Cuisine cuisine1 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation,"deux");
        Cuisine cuisine2 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation,"trois");

        cuisineList.add(cuisine0);
        cuisineList.add(cuisine1);
        cuisineList.add(cuisine2);

        cuisineList.forEach(Thread::start);

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
                        7 : Afficher l'historique
                        8 : se déconnecter""");

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

                        synchronized (commandesEnAttenteDePreparation){
                            commandesEnAttenteDePreparation.add(commande);
                            commandesEnAttenteDePreparation.notifyAll();
//                        cuisineList.get(0).notify();
                        }


                        login = false;
                        System.out.println("La commande a bien était payer, le temps de préparation de la commande est estimé à " + commande.getTempsCommande() + " minutes");
                        System.out.println("""
                                Vous allez être déconnecté.
                                Bonne appétit""");

                    }
                    case 6 -> modifierCommande(commande);
                    case 7 -> afficherHistorique();
                    case 8 -> login = false;
                    default -> System.out.println("Veuillez selectionner une option valide");
                }

            }
        }
        sc.close();

    }

    public static void afficherHistorique(){
        clientConnected.getHistoriqueCommandes().forEach(commande -> System.out.println(commande.toString()));
    }

    public static void modifierCommande(Commande commande){
        Scanner sc = new Scanner(System.in);
        int rep;
        System.out.println("""
                Que voulez-vous faire ? :
                1 : Supprimer un element de la commande
                2 : Retour
                """);

        try {
            rep = Integer.valueOf(sc.nextLine());
        } catch (Exception e) {
            rep = 0;
        }
//        Map<Integer,String> commandMap = new HashMap<>();
        int var = 0;

        Map<Integer,Produit> commandMap = new HashMap<>();

        for (Boisson b : commande.getBoissonList()){
            commandMap.put(var,b);
            var++;
        }
        for (Plat p : commande.getPlatList()){
            commandMap.put(var,p);
            var++;
        }
        for (Accompagnement a : commande.getAccompagnementList()){
            commandMap.put(var,a);
            var++;
        }

        for (Menu m : commande.getMenuList()){
            commandMap.put(var,m);
            var++;
        }

        if(var == 0) {
            System.out.println("Votre commande est vide");
        } else {
            if(rep == 1){
                int repElementASuppr;
                System.out.println("Que voulez-vous supprimer ?");
                commandMap.forEach((i, s) -> System.out.println(i + " " + s.getAffichageProduit()));
                System.out.println(commandMap.size() + " annuler la suppression");

                try {
                    repElementASuppr = Integer.valueOf(sc.nextLine());
                } catch (Exception e) {
                    repElementASuppr = 0;
                }

                if (repElementASuppr != commandMap.size()) {

                    if(commandMap.get(repElementASuppr) instanceof Boisson) commande.getBoissonList().remove(repElementASuppr);
                    if(commandMap.get(repElementASuppr) instanceof Plat) commande.getPlatList().remove(repElementASuppr - commande.getBoissonList().size());
                    if(commandMap.get(repElementASuppr) instanceof Accompagnement) commande.getAccompagnementList().remove(repElementASuppr - commande.getBoissonList().size() + commande.getPlatList().size());
                    if(commandMap.get(repElementASuppr) instanceof Menu) commande.getMenuList().remove(repElementASuppr - (commande.getBoissonList().size() + commande.getPlatList().size() + commande.getAccompagnementList().size()));
                }

                System.out.println(commandMap.get(repElementASuppr) + " a été supprimé de la commande");

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

        if (rep >= platMap.size() || rep < 0) {
            return;
        } else {
            try {
                menu.setPlat((Plat) Plat.getAlimentByName("Save/Plat/",platMap.get(rep)));
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

        if (rep >= accompagnementMap.size() || rep < 0) {
            return;
        } else {
            try {
//                menu.setAccompagnement(Accompagnement.getAccompagnementByName(accompagnementMap.get(rep)));
                menu.setAccompagnement((Accompagnement) Accompagnement.getAlimentByName("Save/Accompagnement/",accompagnementMap.get(rep)));
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

        if (rep >= boissonMap.size() || rep < 0) {
            return;
        } else {
            try {
//                menu.setBoisson(Boisson.getBoissonByName(boissonMap.get(rep)));
                menu.setBoisson((Boisson) Boisson.getAlimentByName("Save/Boisson/",boissonMap.get(rep)));
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
                if(!((Accompagnement) Accompagnement.getAlimentByName("Save/Accompagnement/",map.get(i))).isOnlyMenu()) {
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

        if (rep < finalMap.size() && rep >= 0 ) {
            try {
                commande.accompagnementList.add((Accompagnement) Accompagnement.getAlimentByName("Save/Accompagnement/",finalMap.get(rep)));
                System.out.println(finalMap.get(rep) + " ajouter à la commande");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }


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

                }
            }
        }
        System.out.print((char)27 + "[33m");
        System.out.println("""
                ----------------------------
                
                Bonjour\040""" + clientConnected.getNom() + " (～￣▽￣)～");
    }

    public static void ajouterPlat(Map<Integer,String> map,Commande commande){
        Scanner sc = new Scanner(System.in);
        System.out.println("Quel plat voulez-vous ajouter ? :");
        Map<Integer,String> finalMap = new HashMap<Integer,String>();
        int var = 0;
        for(int i =0; i < map.size(); i++){
            try {
                if(!((Plat) Plat.getAlimentByName("Save/Plat/",map.get(i))).isOnlyMenu()) {
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

        if (rep < finalMap.size() && rep >= 0) {
            try {
                commande.platList.add((Plat) Plat.getAlimentByName("Save/Plat/",finalMap.get(rep)));
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

        if (rep < map.size() && rep >= 0) {
            try {
                commande.boissonList.add((Boisson) Boisson.getAlimentByName("Save/Boisson/",map.get(rep)));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

    }


}
