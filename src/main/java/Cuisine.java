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
        commandeEnCours = commandesEnAttenteDePreparation.getFirst();
        commandesEnAttenteDePreparation.remove(commandesEnAttenteDePreparation.getFirst());
        commandeEnCours.setStatutCommande(1);
        commandeEnCours.setPourcentageAvancement(0);

        notifyAll();
    }



    @Override
    public void run() {
        try {


            while (arret) {
                if (commandeEnCours.getStatutCommande() == 2) {
                    lancerPreparationCommande();

                } else {
                    commandeEnCours.calculerTemps();
                    double tempsPrep = commandeEnCours.getTempsCommande();

                    double tempsPourUnPourcent = tempsPrep / 100;
                    while (commandeEnCours.getPourcentageAvancement() <= 100) {
                        try {
                            Thread.sleep((long) tempsPourUnPourcent);
                        } catch (InterruptedException e) {
                            e.printStackTrace();

                            commandeEnCours.setPourcentageAvancement(commandeEnCours.getPourcentageAvancement() + 1);
                        }

                    }


                }
            }
        } catch (Exception e){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println("en attente d'une commande");
        }

    }

    public void arreter(){
        arret = false;
    }




}
