package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Ressources.Ressource;
import Entites.Ressources.Vegetal;
import Entites.Ressources.Viande;

//Rendement de la consommation de nourriture, herbivore et carnivore
public class Digestion extends Organe {
    //0 herbivore, 1 carnivore
    double RegimeAlimentaire;

    /**
     * Retourne la quantite d'energie extraite par le systeme digestif de la creature
     *
     * @param nourriture : Aliemnt consommé
     * @return energie pouvant etre assimilée
     */
    public double extraireEnergie(Ressource nourriture) {
        double resultat = 0;
        double contenu = nourriture.getQuantiteEnergie();
        if (nourriture instanceof Vegetal) {
            resultat = (1.0 - RegimeAlimentaire) * contenu;
        } else if (nourriture instanceof Viande) {
            resultat = RegimeAlimentaire * contenu;
        }
        return resultat;
    }
}
