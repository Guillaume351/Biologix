package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

//Se proteger des attaques
public class Defensif extends Organe {

    private double puissanceDefense;


    public double getEnergieDepenseeDefense(double volonteDefense) {
        return volonteDefense * this.puissanceDefense * this.getCreatureHote().getFoie().getProportionPv();
    }


    public double getPuissanceDefense(){
        return this.puissanceDefense;
    }
}
