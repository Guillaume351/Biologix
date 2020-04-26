package Entites.Ressources;

public class Arbre extends Vegetal {

    double taille; //la hauteur de l'arbre

    /**
     * Constructeur de l'arbre
     * @param energieMaxStockable
     * @param energieVegetal
     * @param taille
     */
    public Arbre(double energieMaxStockable, double energieVegetal, double taille) {
        super(energieMaxStockable, energieVegetal);
        this.taille = taille;
    }

    void setTaille(double taille) {
        this.taille = taille;
    }

    void grandir(double hauteur) { this.taille += hauteur; }
}
