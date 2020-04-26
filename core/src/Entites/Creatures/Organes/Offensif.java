package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;

//Attaquer
public class Offensif extends Organe {

    private double puissanceAttaque;
    private double volonteAttaque;

    public double getVolonteAttaque(){return this.volonteAttaque;}

    public double getEnergieDepenseeAttaque(double volonteAttaque) {
        return volonteAttaque * this.puissanceAttaque * this.getCreatureHote().getFoie().getProportionPv();
    }


    public double getPuissanceAttaque(){
        return this.puissanceAttaque;
    }

    public void updateOffensif(OutputsCerveau sorties, double dt){
        //this.volonte
    }
}
