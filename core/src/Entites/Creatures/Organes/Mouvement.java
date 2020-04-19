package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

public class Mouvement extends Organe {
    //Organe dedié au mouvement de la créature
    double coeffFrottement;

    /**
     * Calcule l'energie depensee pour réaliser un déplacement (par unité de masse)
     *
     * @param dt      : Temps durant lequel le mouvement est realise
     * @param vitesse : Vitesse de deplacement de la creature
     * @return Energie dépensée par unité de masse
     */
    public double getEnergieDepenseeParUniteMasse(double dt, double vitesse) {
        //Energie = puissance*dt
        //Puissance = force*vitesse
        //force = coeffFrottement*vitesse^2
        return dt * coeffFrottement * Math.pow(Math.abs(vitesse),3.0);
    }
}
