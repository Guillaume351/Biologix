package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

//Attaquer
public class Offensif extends Organe {

    private double puissanceAttaque;

    public double getEnergieDepenseeAttaque(double volonteAttaque) {
        return volonteAttaque * this.puissanceAttaque * this.getCreatureHote().getFoie().getProportionPv();
    }


    public double getPuissanceAttaque(){
        return this.puissanceAttaque;
    }
}
