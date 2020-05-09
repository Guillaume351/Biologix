package Entites.Ressources;

import Entites.Creatures.Creature;
import Environnement.Terrain.Terrain;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class Arbre extends Vegetal {

    private double taille; //la hauteur de l'arbre
    private double tailleMax; //la hauteur max de l'arbre

    public ArrayList<Fruit> fruits;

    /**
     * Constructeur de l'arbre
     *
     * @param energieMaxStockable
     * @param energieVegetal
     * @param taille
     */
    public Arbre(Vector2 position, double energieMaxStockable, double energieVegetal, double taille, Terrain terrain) {
        super(position, energieMaxStockable, energieVegetal, terrain);
        this.taille = taille;
        this.tailleMax = tailleMax;
        this.fruits = new ArrayList<Fruit>();
    }

    void setTaille(double taille) {
        this.taille = taille;
    }

    void setTailleMax(double taille) {
        this.tailleMax = taille;
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
