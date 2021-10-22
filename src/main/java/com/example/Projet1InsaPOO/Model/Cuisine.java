package com.example.Projet1InsaPOO.Model;

import java.util.LinkedList;
import java.util.List;

public class Cuisine extends Thread{

    private List<Commande> commandesEnCoursDePreparation;
    private LinkedList<Commande> commandesEnAttenteDePreparation;
    private Commande commandeEnCours;
    private boolean arret;

    public Cuisine(List<Commande> commandesEnCoursDePreparation, LinkedList<Commande> commandesEnAttenteDePreparation) {
        this.commandesEnCoursDePreparation = commandesEnCoursDePreparation;
        this.commandesEnAttenteDePreparation = commandesEnAttenteDePreparation;
        arret = true;
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
                } else {
                    commandeEnCours.calculerTemps();
                    double tempsPrep = commandeEnCours.getTempsCommande();

                    double tempsPourUnPourcent = tempsPrep / 100 * 6000;
                    while (commandeEnCours.getPourcentageAvancement() < 100) {
                        try {

                            Thread.sleep((long) tempsPourUnPourcent );
                            commandeEnCours.setPourcentageAvancement(commandeEnCours.getPourcentageAvancement() + 1);
                            System.out.println(commandeEnCours.getPourcentageAvancement());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    commandeEnCours.setStatutCommande(2);
                }
            } catch (Exception e) {
//                e.printStackTrace();
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                System.out.print((char) 27 + "[31m");
                System.out.println("\n Cuisine : En attente d'une commande \n");
            }
        }
    }

    public void arreter(){
        arret = false;
    }




}
