package Entites.Ressources;
import java.util.*;

public abstract class Ressource extends Observable {
    double quantiteEnergie;

    public double getQuantiteEnergie() {
        return quantiteEnergie;
    }

    // Savoir si la ressource est une viande ou un végétal
    public abstract String getTypeRessource();

    public abstract void setQuantiteEnergie(double quantiteEnergie);
}
