package Entites.Creatures.Organes;

import Entites.Creatures.Creature;
import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Ressources.Ressource;
import Utils.Position.Localisable;

import java.util.List;

public class Perception extends Organe {
    double luminositeIdeale;
    double adaptationLumiere;

    public double getAdaptationLumiere(){
        return this.adaptationLumiere;
    }

    public double getAdaptationLumiere(double luminosite) {
        double adapt = 1.0 - Math.abs(luminosite - luminositeIdeale);
        return (1.0 - Math.cos(Math.PI * adapt)) / 2.0;
    }

    // TODO : implémenter cette fonction
    public List<Ressource> getRessourcesAccessibles(){
    }

    // TODO : implémenter cette fonction
    public List<Localisable> getCreaturesVisibles(){
    }

    public void updatePerception(OutputsCerveau sorties, double dt, double luminosite){
        this.adaptationLumiere = getAdaptationLumiere(luminosite);
    }

}
