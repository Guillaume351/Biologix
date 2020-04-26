package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;

//Se proteger des attaques
public class Defensif extends Organe {

    private double puissanceDefense;
    private double volonteDefense;


    public double getEnergieDepenseeDefense() {
        return this.volonteDefense * this.puissanceDefense * this.getCreatureHote().getFoie().getProportionPv();
    }


    public double getPuissanceDefense(){
        return this.puissanceDefense;
    }

    public double getVolonteDefense(){return this.volonteDefense;}

    public void updateDefensif(OutputsCerveau sorties, double dt){
        this.volonteDefense = sorties.getVolonteDefense();
    }
}
