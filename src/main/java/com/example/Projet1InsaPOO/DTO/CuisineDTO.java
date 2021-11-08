package com.example.Projet1InsaPOO.DTO;

import com.example.Projet1InsaPOO.Model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CuisineDTO {
    private List<Commande> commandesEnCoursDePreparation = new ArrayList<>();
    private LinkedList<Commande> commandesEnAttenteDePreparation = new LinkedList<>();


    public CuisineDTO() {
        super();
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

    public void addCommandeEnAttenteDePreparation (Commande commande) {
        commandesEnAttenteDePreparation.add(commande);
    }

    public void addCommandeEnCoursDePreparation (Commande commande) {
        commandesEnCoursDePreparation.add(commande);
    }


    public String toString() {
        return "En attente : " + commandesEnAttenteDePreparation
                + ", en preparation : " + commandesEnAttenteDePreparation
                + ", terminée : " + commandesEnCoursDePreparation;

    }

}
