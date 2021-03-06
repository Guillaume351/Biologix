package Entites.Ressources;

import Entites.Entite;
import Environnement.Terrain.Terrain;
import com.badlogic.gdx.math.Vector2;

public abstract class Ressource extends Entite {
    double quantiteEnergie;

    Vector2 orientation;

    Terrain terrain;

    public Ressource(Vector2 position) {
        super(position);
        // TODO : faire une bonne init de la quantite d'énergie
        this.quantiteEnergie = 500;
    }

    public Ressource(Vector2 position, double energie) {
        super(position);
        // TODO : faire une bonne init de la quantite d'énergie
        this.quantiteEnergie = energie;
    }

    public double getQuantiteEnergie() {
        return quantiteEnergie;
    }

    public Terrain getTerrain(){ return this.terrain;}

    // Savoir si la ressource est une viande, un végétal ou un fruit
    public abstract String getTypeRessource();

    public void setQuantiteEnergie(double quantiteEnergie) {
        this.quantiteEnergie = quantiteEnergie;
    }

    public abstract double getTaille();

    public double manger(double energie) {
        double energieDispo = Math.min(energie, quantiteEnergie);
        this.quantiteEnergie = Math.max(0, quantiteEnergie - energie);
        return energieDispo;
    }

    public boolean estToxique() {
        if (this instanceof Fruit) {
            return ((Fruit) this).estEmpoisonne();
        }
        if (this instanceof Viande) {
            return ((Viande) this).estPourrie();
        }
        return true;
    }
}
