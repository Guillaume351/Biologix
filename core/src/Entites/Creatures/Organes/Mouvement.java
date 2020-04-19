package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

public class Mouvement extends Organe {
    //Organe dedié au mouvement de la créature
    double coeffFrottement;

    /**
     * Renvoie l'energie depensee par unité de masse pour réaliser un déplacement a la vitesse dionnee durant un temps dt
     *
     * @param dt      : temps durant lequel le mouvement est realise
     * @param vitesse : vitesse de deplacement de la creature
     * @return energie depensee par unite de masse
     */
    public double getEnergieDepenseeParUniteMasse(double dt, double vitesse) {
        //Energie = puissance*dt
        //Puissance = force*vitesse
        //force = coeffFrottement*vitesse^2
        return dt * coeffFrottement * Math.pow(Math.abs(vitesse),3.0);
    }
}
