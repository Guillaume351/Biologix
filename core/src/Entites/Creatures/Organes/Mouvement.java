package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;

import java.util.Random;

public class Mouvement extends Organe {
    //Organe dedié au mouvement de la créature
    double vitesseMax;
    double potentielNageoire;

    public Mouvement(Random r){
        super(r);
        this.vitesseMax = ConstantesBiologiques.vitesseMaxMin + (ConstantesBiologiques.vitesseMaxMax - ConstantesBiologiques.vitesseMaxMin) * r.nextDouble();
        this.potentielNageoire = r.nextDouble();
    }

    public Mouvement(Mouvement mouvementMere, Mouvement mouvementPere, Random r, double mutation){
        super(mouvementMere, mouvementPere, r, mutation);
        Mouvement alea = new Mouvement(r);
        this.vitesseMax = (mouvementMere.vitesseMax + mouvementPere.vitesseMax + alea.vitesseMax * mutation) / (2 + mutation);
        this.potentielNageoire = (mouvementMere.potentielNageoire + mouvementPere.potentielNageoire + alea.potentielNageoire * mutation) / (2 + mutation);
    }

    public double getVitesseMax(Terrain terrain) {
        if (terrain.estDansEau(this.getCreatureHote())) {
            return vitesseMax * potentielNageoire;
        } else {
            return vitesseMax * (1 - potentielNageoire);
        }
    }

    public boolean estAquatique() {
        return (potentielNageoire > 0.5);
    }

    public boolean estTerrestre() {
        return potentielNageoire <= 0.5;
    }

    public boolean estDansMilieuNaturel(Terrain terrain) {
        boolean dansEau = terrain.estDansEau(this.getCreatureHote());
        return dansEau && this.estAquatique() || !dansEau && this.estTerrestre();
    }

    /**
     * Calcule l'energie depensee pour réaliser un déplacement (par unité de masse)
     *
     * @param dt      : Temps durant lequel le mouvement est realise
     * @param vitesse : Vitesse de deplacement de la creature
     * @return Energie dépensée par unité de masse
     */
    public double getEnergieDepenseeMouvementParUniteDeMasse(double dt, double vitesse) {
        //Energie = puissance*dt
        //Puissance = force*vitesse
        //force = coeffFrottement*vitesse^2
        double result = dt * ConstantesBiologiques.coeffFrottement * Math.pow(Math.abs(vitesse), 3.0);

        return result;

    }
}
