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
    private boolean aquatique;      // Un arbre peut être aquatique

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
        if (this.terrain.estDansEau(this)) {setAquatique(true);
        } else { setAquatique(false);}
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
        if (this.terrain.estDansEau(this)) {setAquatique(true);
        } else { setAquatique(false);}
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
        if (bool) {
            this.aquatique = true;
        } else {
            this.aquatique = false;
        }
    }


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
        Color[] couleurs = {Color.BLUE, Color.RED, Color.GREEN, Color.YELLOW};
        int nombreAleatoire = (int)(Math.random() * couleurs.length);
        super.update(dt);
        grandir(dt);
        tempsDepuisProdFruit += dt;
        tempsDepuisChute += dt;
        if (tempsDepuisProdFruit > tempsProductionFruit) {
            tempsDepuisProdFruit = 0;
            Fruit fruit = new Fruit(rand, couleurs[nombreAleatoire], false, this.terrain, true);
            fruit.setPosition(new Vector2(this.getPosition()));
            ajouterFruit(fruit);
        }
        if (tempsDepuisChute > tempsAvantChute) {
            retirerFruit();
        }
    }

}
