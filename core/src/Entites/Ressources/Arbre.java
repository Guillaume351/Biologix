package Entites.Ressources;

public class Arbre extends Vegetal {

    private double taille; //la hauteur de l'arbre
    private double tailleMax; //la hauteur max de l'arbre

    /**
     * Constructeur de l'arbre
     * @param energieMaxStockable
     * @param energieVegetal
     * @param taille
     */
    public Arbre(double energieMaxStockable, double energieVegetal, double taille) {
        super(energieMaxStockable, energieVegetal);
        this.taille = taille;
        this.tailleMax = tailleMax;
    }

    void setTaille(double taille) {
        this.taille = taille;
    }

    void setTailleMax(double taille) {
        this.tailleMax = taille;
    }

    double getTaille() {return this.taille;}

    double getTailleMax() {return this.tailleMax;}

    void grandir(double hauteur) {
        if (getTaille() + hauteur < getTailleMax() ) {
            this.taille += hauteur;
        } else { setTaille(this.tailleMax); }
    }

}
