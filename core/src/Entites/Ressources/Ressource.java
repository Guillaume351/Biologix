package Entites.Ressources;

import Entites.Entite;

public abstract class Ressource extends Entite {
    double quantiteEnergie;

    public double getQuantiteEnergie() {
        return quantiteEnergie;
    }

    // Savoir si la ressource est une viande ou un végétal
    public abstract String getTypeRessource();

    public abstract void setQuantiteEnergie(double quantiteEnergie);
}
