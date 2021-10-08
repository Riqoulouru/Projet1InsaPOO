import java.util.List;

public class Commande {

    List<Plat> platList;
    List<Boisson> boissonList;
    List<Accompagnement> accompagnementList;
    List<Menu> menuList;
    private double prixTotal;
    private double tempsCommande;
    private int statutCommande;
    private double pourcentageAvancement;


    public Commande() {
        this.tempsCommande = 0;
        this.statutCommande = 0;
        this.pourcentageAvancement = 0;

    }

    public void addTempsCommande(double d){
        tempsCommande += d;
    }

    public void calculerTemps(){
        accompagnementList.forEach((a) -> a.getIngredientlist().forEach((i) -> addTempsCommande(i.getTempsCuisson())));
        platList.forEach((a) -> a.getIngredientlist().forEach((i) -> addTempsCommande(i.getTempsCuisson())));
        menuList.forEach((a) -> {
            a.getAccompagnement().getIngredientlist().forEach((i) -> addTempsCommande(i.getTempsCuisson()));
            a.getPlat().getIngredientlist().forEach((i) -> addTempsCommande(i.getTempsCuisson()));
        });
    }

    public void addPrix(double p){
        tempsCommande += p;
    }

    public void calculerPrix(){
        accompagnementList.forEach((a) -> addPrix(a.getPrix()));
        platList.forEach((p) -> addPrix(p.getPrix()));
        menuList.forEach((a) -> {
            addPrix(a.getAccompagnement().getPrix());
            addPrix(a.getPlat().getPrix());
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
}
