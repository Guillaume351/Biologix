package Entites.Creatures.Organes;

import Entites.Creatures.Creature;
import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Entite;
import Entites.Ressources.Ressource;
import Utils.Position.Localisable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public List<Localisable> getEntitesVisibles(){
        List<Entite> entitesMap = this.getCreatureHote().getTerrain().getEntites();
        return null;
    }

    public List<Localisable> getCreaturesVisibles() {
        List<Localisable> creaVisibles = new ArrayList<>();
        for (Localisable loc : this.getEntitesVisibles()) {
            if (loc instanceof Creature) {
                creaVisibles.add(loc);
            }
        }
        return creaVisibles;
    }

    public List<Localisable> getRessourcessVisibles() {
        List<Localisable> ressourcesVisibles = new ArrayList<>();
        for (Localisable loc : this.getEntitesVisibles()) {
            if (loc instanceof Ressource) {
                ressourcesVisibles.add(loc);
            }
        }
        return ressourcesVisibles;
    }

    public void updatePerception(OutputsCerveau sorties, double dt, double luminosite){
        this.adaptationLumiere = getAdaptationLumiere(luminosite);
    }

}
