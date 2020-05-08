package Entites.Ressources;

import Environnement.Terrain.Terrain;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Fruit extends Vegetal {
    double forcePoison; //les fruits peuvent être empoisonnées, 0 = safe, 0,5 = rend malade, 1 = tue...
    Color couleur;

    /**
     * Constructeur d'un fruit
     *
     * @param energieMaxStockable
     * @param energieVegetal
     * @param forcePoison
     */
    public Fruit(Vector2 position, double energieMaxStockable, double energieVegetal, double forcePoison, Color couleur, Terrain terrain) {
        super(position, energieMaxStockable, energieVegetal, terrain);
        this.forcePoison = forcePoison;
        this.couleur = couleur;
    }

    boolean estEmpoisonne() {
        return forcePoison >= 0.5;
    }

}