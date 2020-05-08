package Entites.Ressources;
import java.awt.Color;
import Environnement.Terrain.Terrain;

public class Fruit extends Vegetal {
    double forcePoison; //les fruits peuvent être empoisonnées, 0 = safe, 0,5 = rend malade, 1 = tue...
    Color couleur;

    /**
     * Constructeur d'un fruit
     * @param energieMaxStockable
     * @param energieVegetal
     * @param forcePoison
     */
    public Fruit(double energieMaxStockable, double energieVegetal, double forcePoison, Color couleur, Terrain terrain) {
        super(energieMaxStockable, energieVegetal, terrain);
        this.forcePoison = forcePoison;
        this.couleur = couleur;
    }

    boolean estEmpoisonne() {
        return forcePoison >= 0.5;
    }

}