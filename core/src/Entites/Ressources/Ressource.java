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

    public double getQuantiteEnergie() {
        return quantiteEnergie;
    }

    public Terrain getTerrain(){ return this.terrain;}

    // Savoir si la ressource est une viande, un végétal ou un fruit
    public abstract String getTypeRessource();

    public void setQuantiteEnergie(double quantiteEnergie) {
        this.quantiteEnergie = quantiteEnergie;
    }

    public double getTaille() {
        return 1;
    }

    public double manger(double energie) {
        double energieDispo = Math.min(energie, quantiteEnergie);
        this.quantiteEnergie = Math.max(0, quantiteEnergie - energie);
        return energieDispo;
    }
}
