package com.example.Projet1InsaPOO.Model;

import java.util.LinkedList;
import java.util.List;

public class Cuisine extends Thread{

    private final List<Commande> commandesEnCoursDePreparation;
    private final LinkedList<Commande> commandesEnAttenteDePreparation;
    private Commande commandeEnCours;
    private boolean arret;
    private final String nom;

    public Cuisine(List<Commande> commandesEnCoursDePreparation, LinkedList<Commande> commandesEnAttenteDePreparation, String nom) {
        this.commandesEnCoursDePreparation = commandesEnCoursDePreparation;
        this.commandesEnAttenteDePreparation = commandesEnAttenteDePreparation;
        arret = true;
        this.nom = nom;
    }

    //permet de prendre la commande la plus ancienne qui est en attente, et de commencer sa préparation
    public synchronized void lancerPreparationCommande(){
        commandeEnCours = commandesEnAttenteDePreparation.getFirst();
        commandesEnAttenteDePreparation.remove(commandesEnAttenteDePreparation.getFirst());
        commandeEnCours.setStatutCommande(1);
        commandeEnCours.setPourcentageAvancement(0);
        notifyAll();
    }

    /**
     * Ici c'est le main des cuisines
     * une cuisine attends tant qu'il n'y a pas de commandes en attente et qu'elle n'est pas déjà en train de préparer un plat
     * quand elle prépare un plat, elle met à jour en directe l'avancement de la commande
     */
    @Override
    public void run() {
        while (arret) {
            try {
                if (commandeEnCours == null || commandeEnCours.getStatutCommande() == 2 ) {
                    lancerPreparationCommande();
                } else if(commandeEnCours != null && commandeEnCours.getStatutCommande() != 2){
                    commandeEnCours.calculerTemps();
                    double tempsPrep = commandeEnCours.getTempsCommande();


                    double tempsPourUnPourcent = tempsPrep / 100 * 6000;
                    while (commandeEnCours.getPourcentageAvancement() < 100) {
                        try {

                            Thread.sleep((long) tempsPourUnPourcent );
                            commandeEnCours.setPourcentageAvancement(commandeEnCours.getPourcentageAvancement() + 1);
                            System.out.println("(Cuisine : "+ nom + ") Commande id : " + commandeEnCours.getIdCommande() + " à " + commandeEnCours.getPourcentageAvancement() + "%");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    commandeEnCours.setStatutCommande(2);
                }
            } catch (Exception e) {
                checkIfWait();
            }
        }
    }

    //La fonction qui permet aux cuisines d'attendre qu'une commande arrive
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

    public LinkedList<Commande> getCommandesEnAttenteDePreparation() {
        return commandesEnAttenteDePreparation;
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
