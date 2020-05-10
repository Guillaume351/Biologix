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
    }

    public double getQuantiteEnergie() {
        return quantiteEnergie;
    }

    // Savoir si la ressource est une viande, un végétal ou un fruit
    public abstract String getTypeRessource();

    public void setQuantiteEnergie(double quantiteEnergie) {
        this.quantiteEnergie = quantiteEnergie;
    }

}
