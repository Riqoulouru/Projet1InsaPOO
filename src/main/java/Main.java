import java.io.*;
import java.util.ArrayList;
import java.util.List;

/***
 *
 * Attention :
 *  - Le fichier SAVE correspond en gros à une mini BDD
 *      Comprennant : les différents aliments / plats / Boisson ... Et les clients.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        InitiliazeAllElements();
    }


    /***
     * Fonction pour la création des produits, etc, si les fichiers n'ont pas déjà été récupérés
     * Fichier de sauvegarde ->
     *         Save / {Accompagnement}{...}
     */
    public static void InitiliazeAllElements() throws IOException {
        //Raclette
        Ingredient fromageRaclette = new Ingredient("Fromage", 500, 0);
        Ingredient pommeDeTerreRaclette = new Ingredient("Pomme_De_Terre", 200, 10);
        Ingredient charcuterie = new Ingredient("Charcuterie", 300, 0);
        List<Ingredient> racletteList = new ArrayList<>(); racletteList.add(fromageRaclette); racletteList.add(pommeDeTerreRaclette); racletteList.add(charcuterie);
        Plat raclette = new Plat("Raclette", 15, racletteList);

        //Poulet Frite
        Ingredient poulet = new Ingredient("Poulet", 200, 60);
        Ingredient pommeDeTerreFrite = new Ingredient("Pomme_De_Terre", 100, 35);
        List<Ingredient> pouletFriteList = new ArrayList<>(); pouletFriteList.add(poulet);pouletFriteList.add(pommeDeTerreFrite);
        Plat pouletFrite = new Plat("Poulet_Frite", 17.5, pouletFriteList);

        //Coca
        Boisson coca = new Boisson("Coca_Cola",5);

        //Eau
        Boisson eau = new Boisson("Eau", 2);

        //Salade
        Ingredient saladeIngredient = new Ingredient("Salade", 50, 2);
        List<Ingredient> saladeList = new ArrayList<>(); saladeList.add(saladeIngredient);
        Accompagnement salade = new Accompagnement("Salade", 1.6, saladeList);

        //Rizoto
        Ingredient riz = new Ingredient("Riz", 50, 15);
        Ingredient zoto = new Ingredient("Zoto", 50, 10);
        List<Ingredient> rizotoList = new ArrayList<>(); rizotoList.add(riz); rizotoList.add(zoto);
        Accompagnement rizoto = new Accompagnement("Rizoto", 5.6, rizotoList);


        Client cl = new Client(1, "Gilbert", "Montagné");


        raclette.saveItem();
        pouletFrite.saveItem();
        coca.saveItem();
        eau.saveItem();
        salade.saveItem();
        rizoto.saveItem();
        cl.saveItem();

    }

}
