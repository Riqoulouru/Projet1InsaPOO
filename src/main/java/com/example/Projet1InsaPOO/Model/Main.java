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


}
