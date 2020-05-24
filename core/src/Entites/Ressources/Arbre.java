package Entites.Ressources;

import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Arbre extends Vegetal {


    Random rand;
    private double croissance;
    private double tempsProductionFruit;
    private double tempsDepuisProdFruit;
    private double tempsAvantChute;
    private double tempsDepuisChute;
    private boolean aquatique;      // Un arbre peut être aquatique

    public ArrayList<Fruit> fruits;  //la liste des fruits qui "peuplent" l'arbre

    /**
     * Constructeur de l'arbre
     *
     * @param : r, terrain
     */
    public Arbre(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())), r, terrain);
        this.croissance = ConstantesBiologiques.croissanceMaxVegetal * r.nextDouble();
        this.tempsProductionFruit = ConstantesBiologiques.tempsProdFruitMin + (ConstantesBiologiques.tempsProdFruitMax - ConstantesBiologiques.tempsProdFruitMin) * r.nextDouble();
        this.fruits = new ArrayList<Fruit>();
        tempsDepuisProdFruit = 0;
        rand = r;
        if (this.terrain.estDansEau(this)) {setAquatique(true);
        } else { setAquatique(false);}
    }

    /**
     * Constructeur de l'arbre à partir d'un fruit
     * @param r
     * @param fruit
     */
    public Arbre(Random r, Fruit fruit) {
        super(fruit.getPosition(), r, fruit.getTerrain(), fruit.getQuantiteEnergie());
        this.croissance = ConstantesBiologiques.croissanceMaxVegetal * r.nextDouble();
        this.tempsProductionFruit = ConstantesBiologiques.tempsProdFruitMin + (ConstantesBiologiques.tempsProdFruitMax - ConstantesBiologiques.tempsProdFruitMin) * r.nextDouble();
        this.fruits = new ArrayList<Fruit>();
        tempsDepuisProdFruit = 0;
        rand = r;
        if (this.terrain.estDansEau(this)) {setAquatique(true);
        } else { setAquatique(false);}
    }





    /**
     * Pour savoit si un arbre est aquatique ou non
     * @return
     */
    boolean estAquatique() {return this.aquatique;}

    /**
     * Définir l'état aquatique d'un arbre.
     * @param bool
     */
    void setAquatique(boolean bool) {
        this.aquatique = bool;
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

    /**
     * Mide à jour du cycle arre/fruit au cours du temps
     * @param dt
     */
    public void update(double dt) {
        Color[] couleurs = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
        int nombreAleatoire = (int)(Math.random() * couleurs.length);
        super.update(dt);
        tempsDepuisProdFruit += dt;
        tempsDepuisChute += dt;
        if (tempsDepuisProdFruit > tempsProductionFruit) {
            tempsDepuisProdFruit = 0;
            Fruit fruit = new Fruit(rand, couleurs[nombreAleatoire], rand.nextBoolean(), this.terrain, true);
            fruit.setPosition(new Vector2(this.getPosition()));
            ajouterFruit(fruit);
        }
        if (tempsDepuisChute > tempsAvantChute) {
            retirerFruit();
        }
        if (quantiteEnergie <= 0) {
            this.getTerrain().retirerEntite(this);
        }
    }

}
