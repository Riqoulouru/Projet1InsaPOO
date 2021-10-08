import java.util.LinkedList;
import java.util.List;

public class Cuisine extends Thread{

    private List<Commande> commandesEnCoursDePreparation;
    private LinkedList<Commande> commandesEnAttenteDePreparation;
    private Commande commandeEnCours;

    public Cuisine(List<Commande> commandesEnCoursDePreparation, LinkedList<Commande> commandesEnAttenteDePreparation) {
        this.commandesEnCoursDePreparation = commandesEnCoursDePreparation;
        this.commandesEnAttenteDePreparation = commandesEnAttenteDePreparation;
    }

    public synchronized void addCommandeAFaire(Commande c){
        if (commandesEnCoursDePreparation.size() <= 3) {
            commandesEnCoursDePreparation.add(c);
        } else {
            commandesEnAttenteDePreparation.add(c);
        }
    }

    public synchronized void lancerPreparationCommande(){
        commandeEnCours = commandesEnAttenteDePreparation.getFirst();
        commandesEnAttenteDePreparation.remove(commandesEnAttenteDePreparation.getFirst());

        commandeEnCours.setStatutCommande(1);
        commandeEnCours.setPourcentageAvancement(0);
    }



    @Override
    public void run() {
        lancerPreparationCommande();



    }




}
