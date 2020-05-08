package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Environnement.Terrain.Terrain;

import java.util.Random;

public class Mouvement extends Organe {
    //Organe dedié au mouvement de la créature
    double coeffFrottement;
    double vitesseMax;
    double potentielNageoire;

    public Mouvement(Random r){
        super(r);
        //this.coeffFrottement =;
        //this.potentielNageoire = ;
    }

    public Mouvement(Mouvement mouvementMere, Mouvement mouvementPere, Random r, double mutation){
        super(mouvementMere, mouvementPere, r, mutation);
    }

    public double getVitesseMax(Terrain terrain) {
        if (terrain.estDansEau(this.getCreatureHote())) {
            return vitesseMax * potentielNageoire;
        } else {
            return vitesseMax * (1 - potentielNageoire);
        }
    }

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
        return dt * coeffFrottement * Math.pow(Math.abs(vitesse), 3.0) / this.getMasse(this.getCreatureHote().getAge());
    }
}
