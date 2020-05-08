package Entites.Ressources;

import Entites.Entite;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import Utils.Position.Localisable;
import Utils.Position.Localisateur;
import com.badlogic.gdx.math.Vector2;

public abstract class Ressource extends Entite {
    double quantiteEnergie;

    Terrain terrain;

    public double getQuantiteEnergie() {
        return quantiteEnergie;
    }

    // Savoir si la ressource est une viande ou un végétal
    public abstract String getTypeRessource();

    public void setQuantiteEnergie(double quantiteEnergie) { this.quantiteEnergie = quantiteEnergie; }

}
