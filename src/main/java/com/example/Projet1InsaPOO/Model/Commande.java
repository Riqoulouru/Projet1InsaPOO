package com.example.Projet1InsaPOO.Model;

import com.example.Projet1InsaPOO.Model.Plat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Commande implements Serializable {
    private static int lastId = -1;
    private final int idCommande;
    List<Plat> platList;
    List<Boisson> boissonList;
    List<Accompagnement> accompagnementList;
    List<Menu> menuList;
    private double prixTotal;
    private double tempsCommande;
    private int statutCommande;
    private double pourcentageAvancement;
    private String dateValidation;

    public Commande() {
        this.idCommande = lastId + 1;
        lastId += 1;
        this.tempsCommande = 0;
        this.statutCommande = 0;
        this.pourcentageAvancement = 0;
        platList = new ArrayList<>();
        boissonList = new ArrayList<>();
        accompagnementList = new ArrayList<>();
        menuList = new ArrayList<>();
    }

    public void addTempsCommande(double d){
        tempsCommande += d;
    }

    public void calculerTemps(){
        accompagnementList.forEach((a) -> a.getIngredientlist().forEach((i) -> addTempsCommande(i.getTempsCuisson())));
        platList.forEach((a) -> a.getIngredientlist().forEach((i) -> {

            addTempsCommande(i.getTempsCuisson());
        }));
        menuList.forEach((a) -> {
            a.getAccompagnement().getIngredientlist().forEach((i) -> {
                addTempsCommande(i.getTempsCuisson());
            });
            a.getPlat().getIngredientlist().forEach((i) -> {
                addTempsCommande(i.getTempsCuisson());
            });
        });
    }

    public void addPrix(double p){
        prixTotal += p;
    }

    public void calculerPrix(){
        prixTotal = 0;
        boissonList.forEach((a) -> addPrix(a.getPrix()));
        accompagnementList.forEach((a) -> addPrix(a.getPrix()));
        platList.forEach((p) -> addPrix(p.getPrix()));
        menuList.forEach((a) -> {
            addPrix(a.getAccompagnement().getPrix());
            addPrix(a.getPlat().getPrix());
            addPrix(a.getBoisson().getPrix());
        });
    }




    public double getTempsCommande() {

        return tempsCommande;
    }

    public void setTempsCommande(double tempsCommande) {
        this.tempsCommande = tempsCommande;
    }

    public List<Plat> getPlatList() {
        return platList;
    }

    public List<Boisson> getBoissonList() {
        return boissonList;
    }

    public List<Accompagnement> getAccompagnementList() {
        return accompagnementList;
    }

    public List<Menu> getMenuList() {
        return menuList;
    }

    public void setPlatList(List<Plat> platList) {
        this.platList = platList;
    }

    public void setBoissonList(List<Boisson> boissonList) {
        this.boissonList = boissonList;
    }

    public void setAccompagnementList(List<Accompagnement> accompagnementList) {
        this.accompagnementList = accompagnementList;
    }

    public void setMenuList(List<Menu> menuList) {
        this.menuList = menuList;
    }

    public double getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(double prixTotal) {
        this.prixTotal = prixTotal;
    }

    public int getStatutCommande() {
        return statutCommande;
    }

    public void setStatutCommande(int statutCommande) {
        this.statutCommande = statutCommande;
    }

    public double getPourcentageAvancement() {
        return pourcentageAvancement;
    }

    public void setPourcentageAvancement(double pourcentageAvancement) {
        this.pourcentageAvancement = pourcentageAvancement;
    }

    public int getIdCommande() { return idCommande; }

    @Override
    public String toString() {
        StringBuilder allPlat = new StringBuilder("");
        for(Plat p : platList){
            allPlat.append(" ").append(p.getAffichageProduit());
        }

        StringBuilder allBoisson = new StringBuilder("");
        for(Boisson b : boissonList){
            allBoisson.append(" ").append(b.getAffichageProduit());
        }

        StringBuilder allAccompagnement = new StringBuilder("");
        for(Accompagnement a : accompagnementList){
            allAccompagnement.append(" ").append(a.getAffichageProduit());
        }

        StringBuilder allMenu = new StringBuilder("");
        for(Menu m : menuList){
            allMenu.append(" ").append(m.getAffichageProduit());
        }

        return "Commande : " +
                "platList=" + allPlat +
                ", boissonList=" + allBoisson +
                ", accompagnementList=" + allAccompagnement +
                ", menuList=" + allMenu +
                ", prixTotal=" + prixTotal;
    }

    public String getDateValidation() {
        return dateValidation;
    }

    public void setDateValidation(String dateValidation) {
        this.dateValidation = dateValidation;
    }

}
