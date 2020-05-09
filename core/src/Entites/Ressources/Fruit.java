package Entites.Ressources;

import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Fruit extends Vegetal {
    double forcePoison; //les fruits peuvent être empoisonnées, 0 = safe, 0,5 = rend malade, 1 = tue...
    Color couleur;
    boolean estDansArbre;
    double dureeDeVie = ConstantesBiologiques.dureeDeVieFruit;

    /**
     * Constructeur d'un fruit
     *
     * @param energieMaxStockable
     * @param energieVegetal
     * @param forcePoison
     */
    public Fruit(Vector2 position, double energieMaxStockable, double energieVegetal, double forcePoison, Color couleur, Terrain terrain, boolean estDansArbre) {
        super(position, energieMaxStockable, energieVegetal, terrain);
        this.forcePoison = forcePoison;
        this.couleur = couleur;
        this.estDansArbre = estDansArbre;
    }

    boolean estEmpoisonne() {
        return forcePoison >= ConstantesBiologiques.forcePoisonIntermediaire;
    }

    public void evolutionFruit(double dt) {
            setEnergieVegetal(this.getEnergeVegetal() - this.getEnergeVegetal()*dt);       // ??? pas trop sûre de moi
    }

}