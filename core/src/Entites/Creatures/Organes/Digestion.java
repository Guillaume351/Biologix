package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Ressources.Ressource;
import Entites.Ressources.Vegetal;
import Entites.Ressources.Viande;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;

import java.util.Random;

//Rendement de la consommation de nourriture, herbivore et carnivore
public class Digestion extends Organe {
    public double getRegimeAlimentaire() {
        return regimeAlimentaire;
    }

    //0 herbivore, 1 carnivore
    double regimeAlimentaire;

    public Digestion(Random r){
        super(r);
        this.regimeAlimentaire = r.nextDouble();
    }

    public Digestion(Digestion digestionMere, Digestion digestionPere, Random r, double mutation){
        super(digestionMere, digestionPere, r, mutation);
        Digestion digestionAlea = new Digestion(r);
        this.regimeAlimentaire = (digestionMere.regimeAlimentaire + digestionPere.regimeAlimentaire + digestionAlea.regimeAlimentaire * mutation)/(2 + mutation);
    }

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
            resultat = (1.0 - regimeAlimentaire) * contenu;
        } else if (nourriture instanceof Viande) {
            resultat = regimeAlimentaire * contenu;
        }
        return resultat;
    }
}
