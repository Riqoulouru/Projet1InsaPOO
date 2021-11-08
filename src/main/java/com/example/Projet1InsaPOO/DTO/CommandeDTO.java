package com.example.Projet1InsaPOO.DTO;

import com.example.Projet1InsaPOO.Model.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CommandeDTO {
    private List<Commande> commandesEnCours = new ArrayList<>();


    public CommandeDTO() {
        super();
    }


    public List<Commande> getCommandesEnCours() {
        return commandesEnCours;
    }

    public void setCommandesEnCours(List<Commande> commandesEnCours) {
        commandesEnCours = commandesEnCours;
    }

    public void addCommandeEnCours(Commande commande) {
        commandesEnCours.add(commande);
    }
}
