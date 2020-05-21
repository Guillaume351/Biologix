package Entites.Ressources;

import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Arbre extends Vegetal {

    private double taille; //la hauteur de l'arbre
    private double tailleMax; //la hauteur max de l'arbre
    Random rand;
    private double croissance;
    private double tempsProductionFruit;
    private double tempsDepuisProdFruit;
    private double tempsAvantChute;
    private double tempsDepuisChute;

    public ArrayList<Fruit> fruits;

    /**
     * Constructeur de l'arbre
     *
     * @param : r, terrain
     */
    public Arbre(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())), r, terrain);
        this.taille = ConstantesBiologiques.tailleArbreMin; // au début
        this.tailleMax = ConstantesBiologiques.tailleArbreMin + (ConstantesBiologiques.tailleArbreMax - ConstantesBiologiques.tailleArbreMin) * r.nextDouble();
        this.croissance = ConstantesBiologiques.croissanceMaxVegetal * r.nextDouble();
        this.tempsProductionFruit = ConstantesBiologiques.tempsProdFruitMin + (ConstantesBiologiques.tempsProdFruitMax - ConstantesBiologiques.tempsProdFruitMin) * r.nextDouble();
        this.fruits = new ArrayList<Fruit>();
        tempsDepuisProdFruit = 0;
        rand = r;
    }

    public Arbre(Random r, Fruit fruit) {
        super(fruit.getPosition(), r, fruit.getTerrain(), fruit.getQuantiteEnergie());
        this.taille = ConstantesBiologiques.tailleArbreMin; // au début
        this.tailleMax = ConstantesBiologiques.tailleArbreMin + (ConstantesBiologiques.tailleArbreMax - ConstantesBiologiques.tailleArbreMin) * r.nextDouble();
        this.croissance = ConstantesBiologiques.croissanceMaxVegetal * r.nextDouble();
        this.tempsProductionFruit = ConstantesBiologiques.tempsProdFruitMin + (ConstantesBiologiques.tempsProdFruitMax - ConstantesBiologiques.tempsProdFruitMin) * r.nextDouble();
        this.fruits = new ArrayList<Fruit>();
        tempsDepuisProdFruit = 0;
        rand = r;
    }

    void setTaille(double taille) {
        this.taille = taille;
    }

    void setTailleMax(double taille) {
        this.tailleMax = ConstantesBiologiques.tailleArbreMax;
    }

    public double getTaille() {
        return this.taille;
    }

    double getTailleMax() {return this.tailleMax;}


    private void grandir(double dt) {
        if (taille + croissance * dt < tailleMax) {
            taille += croissance * dt;
        } else {
            taille = tailleMax;
        }
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
    void retirerFruit() {
        tempsDepuisChute = 0;
        if (fruits.size() != 0) {
            tempsAvantChute = ConstantesBiologiques.tempsEntreChuteFruitMax * rand.nextDouble();
            Fruit fruit = fruits.get(rand.nextInt(fruits.size()));
            this.fruits.remove(fruit);
            fruit.setEstDansArbre(false);
            double rayonChute = rand.nextDouble() * ConstantesBiologiques.rayonChuteFruitMax;
            double teta = rand.nextDouble() * Math.PI * 2.0;
            fruit.getPosition().add(new Vector2((float) (rayonChute * Math.cos(teta)), (float) (rayonChute * Math.sin(teta))));
            this.terrain.ajouterEntite(fruit);
        }
    }

    public void update(double dt) {
        super.update(dt);
        grandir(dt);
        tempsDepuisProdFruit += dt;
        tempsDepuisChute += dt;
        if (tempsDepuisProdFruit > tempsProductionFruit) {
            tempsDepuisProdFruit = 0;
            Fruit fruit = new Fruit(rand, Color.RED, false, this.terrain, true);
            fruit.setPosition(new Vector2(this.getPosition()));
            ajouterFruit(fruit);
        }
        if (tempsDepuisChute > tempsAvantChute) {
            retirerFruit();
        }
    }

}
