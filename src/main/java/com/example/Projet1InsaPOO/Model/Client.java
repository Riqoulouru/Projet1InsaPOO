package com.example.Projet1InsaPOO.Model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Client implements Serializable {

    private int id;
    private String nom;
    private String numClient;
    private List<Commande> listCommandes = new ArrayList<>();
    private List<Commande> historiqueCommandes = new ArrayList<>();

    public Client(Integer id, String nom, String numClient) {
        this.id = id;
        this.nom = nom;
        this.numClient = numClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNumClient() {
        return numClient;
    }

    public void setNumClient(String numClient) {
        this.numClient = numClient;
    }

    public List<Commande> getListCommandes() {
        return listCommandes;
    }

    public void setListCommandes(List<Commande> listCommandes) {
        this.listCommandes = listCommandes;
    }

    public List<Commande> getHistoriqueCommandes() {
        return historiqueCommandes;
    }

    public void setHistoriqueCommandes(List<Commande> historiqueCommandes) {
        this.historiqueCommandes = historiqueCommandes;
    }

    public void addToHistorique(Commande commande){
        historiqueCommandes.add(commande);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /***
     *
     * Sauvegarder un client par rapport à son Id en nom de fichier
     */
    public void saveItem() throws IOException {
        FileOutputStream save = new FileOutputStream( "Save/Client/" + this.id + ".ser");
        ObjectOutput oos = new ObjectOutputStream(save);

        oos.writeObject(this);

    }


    /***
     *
     * @param id : l'id du client à récupérer
     * @return : Le client correspondant à l'id
     */
    public static Client getClientById(Integer id) throws IOException, ClassNotFoundException {

        File save = new File("Save/Client/" + id + ".ser");
        FileInputStream charger = new FileInputStream(save);
        ObjectInput ois = new ObjectInputStream(charger);

        return (Client) ois.readObject();
    }
}