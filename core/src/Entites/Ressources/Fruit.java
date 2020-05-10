package Entites.Ressources;

import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.Random;

public class Fruit extends Vegetal {
    boolean estEmpoisonne; //les fruits peuvent être empoisonnées
    Color couleur;
    boolean estDansArbre;
    double dureeDeVie = ConstantesBiologiques.dureeDeVieFruit;

    /**
     * Constructeur d'un fruit
     *
     * @param
     */
    public Fruit(Random r, Color couleur, boolean estEmpoisonne, Terrain terrain, boolean estDansArbre) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())), r, terrain);
        this.estEmpoisonne = estEmpoisonne;
        this.couleur = couleur;
        this.estDansArbre = estDansArbre;
    }

    boolean estEmpoisonne() {
        return estEmpoisonne;
    }

    public void evolutionFruit(double dt) {
            setEnergieVegetal(this.getEnergeVegetal() - this.getEnergeVegetal()*dt);       // ??? pas trop sûre de moi
    }

}