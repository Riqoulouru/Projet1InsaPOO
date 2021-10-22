package com.example.Projet1InsaPOO;

import com.example.Projet1InsaPOO.Model.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class Projet1InsaPooApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(Projet1InsaPooApplication.class, args);

		//Création de la "BDD"
		initiliazeAllElements();


		List<Commande> commandesEnCoursDePreparation = new ArrayList<>();
		LinkedList<Commande> commandesEnAttenteDePreparation = new LinkedList<>();

		//Création des cuisines
		Cuisine cuisine0 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);
		Cuisine cuisine1 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);
		Cuisine cuisine2 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);

		cuisine0.start();
		cuisine1.start();
		cuisine2.start();

	}


	/***
	 * Fonction pour la création des produits, etc, si les fichiers n'ont pas déjà été récupérés
	 * Fichier de sauvegarde ->
	 *         Save / {Accompagnement}{...}
	 */
	public static void initiliazeAllElements() throws IOException {
		//Raclette
		Ingredient fromageRaclette = new Ingredient("Fromage-raclette", 500, 0);
		Ingredient pommeDeTerreRaclette = new Ingredient("Pomme_De_Terre", 200, 10);
		Ingredient charcuterie = new Ingredient("Charcuterie", 300, 0);
		List<Ingredient> racletteList = new ArrayList<>(); racletteList.add(fromageRaclette); racletteList.add(pommeDeTerreRaclette); racletteList.add(charcuterie);
		Plat raclette = new Plat("Raclette", 15, racletteList,false);

		//Poulet Frite
		Ingredient poulet = new Ingredient("Poulet", 200, 60);
		Ingredient pommeDeTerreFrite = new Ingredient("Pomme_De_Terre", 100, 35);
		List<Ingredient> pouletFriteList = new ArrayList<>(); pouletFriteList.add(poulet);pouletFriteList.add(pommeDeTerreFrite);
		Plat pouletFrite = new Plat("Poulet_Frite", 17.5, pouletFriteList,false);

		//Burger
		Ingredient fromage = new Ingredient("Fromage", 500, 0);
		Ingredient steak = new Ingredient("Steak", 300, 10);
		Ingredient tomate = new Ingredient("Tomate", 300, 0);
		Ingredient saladeBurger = new Ingredient("Salade", 300, 0);

		List<Ingredient> burgerList = new ArrayList<>(); burgerList.add(fromage); burgerList.add(steak); burgerList.add(tomate); burgerList.add(saladeBurger);
		Plat burger = new Plat("Burger", 7, burgerList,true);

		//Coca
		Boisson coca = new Boisson("Coca_Cola",5);

		//Eau
		Boisson eau = new Boisson("Eau", 2);

		//Salade
		Ingredient saladeIngredient = new Ingredient("Salade", 50, 2);
		List<Ingredient> saladeList = new ArrayList<>(); saladeList.add(saladeIngredient);
		Accompagnement salade = new Accompagnement("Salade", 1.6, saladeList,false);

		//Rizoto
		Ingredient riz = new Ingredient("Riz", 50, 15);
		Ingredient zoto = new Ingredient("Zoto", 50, 10);
		List<Ingredient> rizotoList = new ArrayList<>(); rizotoList.add(riz); rizotoList.add(zoto);
		Accompagnement rizoto = new Accompagnement("Rizoto", 5.6, rizotoList,false);

		//Frites
		Ingredient patates = new Ingredient("Patates", 100, 15);
		Ingredient sel = new Ingredient("Sel", 10, 0);
		List<Ingredient> fritesList = new ArrayList<>(); rizotoList.add(patates); rizotoList.add(sel);
		Accompagnement frites = new Accompagnement("Frites", 2.5, fritesList,true);

		Client cl = new Client(1, "Gilbert", "Montagné");


		raclette.saveItem();
		pouletFrite.saveItem();
		coca.saveItem();
		eau.saveItem();
		salade.saveItem();
		rizoto.saveItem();
		cl.saveItem();
		burger.saveItem();
		frites.saveItem();


	}
}

