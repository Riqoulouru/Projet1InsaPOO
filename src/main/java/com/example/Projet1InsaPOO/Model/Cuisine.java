package com.example.Projet1InsaPOO.Model;

import java.util.LinkedList;
import java.util.List;

public class Cuisine extends Thread{

    private List<Commande> commandesEnCoursDePreparation;
    private LinkedList<Commande> commandesEnAttenteDePreparation;
    private Commande commandeEnCours;
    private boolean arret;
    private String nom;

    public Cuisine(List<Commande> commandesEnCoursDePreparation, LinkedList<Commande> commandesEnAttenteDePreparation, String nom) {
        this.commandesEnCoursDePreparation = commandesEnCoursDePreparation;
        this.commandesEnAttenteDePreparation = commandesEnAttenteDePreparation;
        arret = true;
        this.nom = nom;
    }


    public synchronized void lancerPreparationCommande(){
//        System.out.println(commandesEnAttenteDePreparation.size());
        commandeEnCours = commandesEnAttenteDePreparation.getFirst();
        commandesEnAttenteDePreparation.remove(commandesEnAttenteDePreparation.getFirst());
        commandeEnCours.setStatutCommande(1);
        commandeEnCours.setPourcentageAvancement(0);
        notifyAll();
    }

    @Override
    public void run() {
        while (arret) {
            try {
                if (commandeEnCours == null || commandeEnCours.getStatutCommande() == 2 ) {
                    lancerPreparationCommande();
                    System.out.println("test1");
                } else if(commandeEnCours != null && commandeEnCours.getStatutCommande() != 2){
                    System.out.println("test2");
                    commandeEnCours.calculerTemps();
                    double tempsPrep = commandeEnCours.getTempsCommande();

                    double tempsPourUnPourcent = tempsPrep / 100 * 6000;
                    while (commandeEnCours.getPourcentageAvancement() < 100) {
                        try {

                            Thread.sleep((long) tempsPourUnPourcent );
                            commandeEnCours.setPourcentageAvancement(commandeEnCours.getPourcentageAvancement() + 1);
//                            System.out.println(commandeEnCours.getPourcentageAvancement());
                            System.out.println("(Cuisine : "+ nom + ") Commande id : " + commandeEnCours.getIdCommande() + " à " + commandeEnCours.getPourcentageAvancement() + "%");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    commandeEnCours.setStatutCommande(2);
                }
            } catch (Exception e) {
//                e.printStackTrace();
                checkIfWait();
            }
        }
    }

    public void checkIfWait(){
        if(commandesEnAttenteDePreparation.size() == 0){
            try {
                System.out.print((char) 27 + "[31m");
                System.out.println("\n Cuisine : En attente d'une commande \n");
                synchronized (commandesEnAttenteDePreparation){
                    commandesEnAttenteDePreparation.wait();
                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
//        notifyAll();
    }


    public void arreter(){
        arret = false;
    }


    public List<Commande> getCommandesEnCoursDePreparation() {
        return commandesEnCoursDePreparation;
    }

    public void setCommandesEnCoursDePreparation(List<Commande> commandesEnCoursDePreparation) {
        this.commandesEnCoursDePreparation = commandesEnCoursDePreparation;
    }

    public LinkedList<Commande> getCommandesEnAttenteDePreparation() {
        return commandesEnAttenteDePreparation;
    }

    public void setCommandesEnAttenteDePreparation(LinkedList<Commande> commandesEnAttenteDePreparation) {
        this.commandesEnAttenteDePreparation = commandesEnAttenteDePreparation;
    }

    public boolean isArret() {
        return arret;
    }

    public void setArret(boolean arret) {
        this.arret = arret;
    }

    public Commande getCommandeEnCours() {
        return commandeEnCours;
    }

    public void setCommandeEnCours(Commande commandeEnCours) {
        this.commandeEnCours = commandeEnCours;
    }

    public void addCommandesEnAttenteDePreparation(Commande commande) {
        commandesEnAttenteDePreparation.add(commande);
    }
}
