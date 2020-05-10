package Entites.Ressources;

import Entites.Creatures.Creature;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arbre extends Vegetal {

    private double taille; //la hauteur de l'arbre
    private double tailleMax; //la hauteur max de l'arbre

    public ArrayList<Fruit> fruits;

    /**
     * Constructeur de l'arbre
     *
     * @param : r, terrain
     */
    public Arbre(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())), r, terrain);
        this.taille = ConstantesBiologiques.tailleArbreMin; // au début
        this.tailleMax = getTailleMax();
        this.fruits = new ArrayList<Fruit>();
    }

    void setTaille(double taille) {
        this.taille = taille;
    }

    void setTailleMax(double taille) {
        this.tailleMax = ConstantesBiologiques.tailleArbreMax;
    }

    double getTaille() {return this.taille;}

    double getTailleMax() {return this.tailleMax;}

    void grandir(double hauteur, double dt) {
        grandir(dt);
        if (getTaille() + hauteur < getTailleMax() ) {
            this.taille += hauteur*dt;
        } else { setTaille(this.tailleMax*dt); }
    }

    /**
     * ajoute des fruits dans l'arbre
     */
    void ajouterFruit(Fruit fruit) {
         this.fruits.add(fruit);
    }

    /**
     * retire des fruits dans l'arbre
     */
    void retirerFruit(Fruit fruit) {
        this.fruits.remove(fruit);
    }

}
