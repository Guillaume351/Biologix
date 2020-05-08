package Entites.Creatures.Organes;

import Entites.Creatures.Creature;
import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Entite;
import Entites.Ressources.Ressource;
import Utils.ConstantesBiologiques;
import Utils.Position.Localisable;
import Utils.Position.Localisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perception extends Organe {
    double luminositeIdeale;
    double adaptationLumiere;
    double distanceVue;
    double champVision;

    public Perception(Random r){
        super(r);
        this.luminositeIdeale = r.nextDouble();
        this.adaptationLumiere = 0;
        this.distanceVue = 0;
        this.champVision = 0;

    }

    public Perception(Perception perceptionMere, Perception perceptionPere, Random r, double mutation){
        super(perceptionMere, perceptionPere, r, mutation);
        this.adaptationLumiere = 0;
        this.distanceVue = 0;
        this.champVision = 0;
        this.luminositeIdeale = (perceptionMere.luminositeIdeale + perceptionPere.luminositeIdeale + r.nextDouble() * mutation) / (2 + mutation);
    }

    public double getChampVisionOptimal() {
        return Math.min(ConstantesBiologiques.densiteChampVision * this.getMasse(this.getCreatureHote().getAge()), Math.PI);
    }

    public double getDistanceVisionOptimal() {
        return ConstantesBiologiques.densiteDistVision * this.getMasse(this.getCreatureHote().getAge());
    }



    public double getAdaptationLumiere(){
        return this.adaptationLumiere;
    }

    public double getAdaptationLumiere(double luminosite) {
        double adapt = 1.0 - Math.abs(luminosite - luminositeIdeale);
        return (1.0 - Math.cos(Math.PI * adapt)) / 2.0;
    }

    public List<Localisable> getEntitesVisibles(){
        List<Entite> entitesMap = this.getCreatureHote().getTerrain().getEntites();
        return Localisateur.getDansChampVision(this.getCreatureHote().getPosition(), this.getCreatureHote().getOrientation(), (List) entitesMap, this.champVision, this.distanceVue);
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
        this.champVision = sorties.getChampVision();
        this.distanceVue = sorties.getDistanceVision();
    }

}
