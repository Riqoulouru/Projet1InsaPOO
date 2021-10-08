import java.io.*;
import java.util.*;

/***
 *
 * Attention :
 *  - Le fichier SAVE correspond en gros à une mini BDD
 *      Comprennant : les différents aliments / plats / Boisson ... Et les clients.
 */
public class Main {

    public static void main(String[] args) throws IOException {
//        InitiliazeAllElements();

        Map<Integer,String> accompagnementMap = getSavesByPath("Save\\Accompagnement\\");
        Map<Integer,String> platMap = getSavesByPath("Save\\Plat\\");
        Map<Integer,String> BoissonMap = getSavesByPath("Save\\Boisson\\");
        Map<Integer,String> clientMap = getSavesByPath("Save\\Client\\");
//        accompagnementMap.forEach((r,t) -> System.out.println(r + "   " + t));

        List<Commande> commandesEnCoursDePreparation = new ArrayList<>();
        LinkedList<Commande> commandesEnAttenteDePreparation = new LinkedList<>();

        Cuisine cuisine0 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);
        Cuisine cuisine1 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);
        Cuisine cuisine2 = new Cuisine(commandesEnCoursDePreparation,commandesEnAttenteDePreparation);

        cuisine0.start();
        cuisine1.start();
        cuisine2.start();


        Scanner sc = new Scanner(System.in);
        boolean login = false;

        while (!login){
            System.out.println("Veuillez saisir votre id:");
            login = true;

            while (login){


                System.out.println("""
                        Que voulez vous faire :
                        1 : Commander un menu
                        2 : Commander un accompagnement
                        3 : Commander une boisson
                        4 : Commander un plat
                        5 : se déconnecter""");

                int rep;
                try {
                    rep = Integer.valueOf(sc.nextLine());
                } catch (Exception e){
                    rep = 0;
                }


                switch (rep){
                    case 1 -> System.out.println("menu");
                    case 2 -> System.out.println("accompagnement");
                    case 3 -> System.out.println("boisson");
                    case 4 -> System.out.println("plat");
                    case 5 -> login = false;
                    default -> System.out.println("Veuillez selectionner une option valide");
                }

            }
        }

    }

    public void ajouterAccompagnement(){

    }

    public void ajouterPlat(){

    }

    public void ajouterBoisson(){

    }

    public static Map<Integer,String> getSavesByPath(String path){
        File dossier=new File(path);
        File[] liste_saves=dossier.listFiles();
        assert liste_saves != null;

        boolean exist = Objects.requireNonNull(liste_saves).length!=0;

        Map<Integer,String> accompagnementMap = new HashMap<>();
        int i = 0;
        if (exist) {
            for (File file : liste_saves) {
                accompagnementMap.put(i,getRealName(file.getName()));
                i++;
            }
        }
        return accompagnementMap;
    }


    public static String getRealName(String original_name){
        String file_name = "";
        int i = 0;
        while(original_name.charAt(i) != '.'){
            file_name += original_name.charAt(i);
            i++;
        }
        return file_name;
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
