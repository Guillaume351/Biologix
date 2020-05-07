package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;

import java.util.Random;

//Attaquer
public class Offensif extends Organe {

    private double puissanceAttaque;
    private double volonteAttaque;

    public Offensif(Random r){
        //super(r);
        //this.puissanceAttaque = ;
        //this.volonteAttaque = ;
    }

    public Offensif(Offensif offensifMere, Offensif offensifPere, Random r){

    }

    public double getVolonteAttaque(){return this.volonteAttaque;}

    public double getEnergieDepenseeAttaque() {
        return this.volonteAttaque * this.puissanceAttaque * this.getCreatureHote().getFoie().getProportionPv();
    }


    public double getPuissanceAttaque(){
        return this.puissanceAttaque;
    }

    public void updateOffensif(OutputsCerveau sorties, double dt){
        this.volonteAttaque = sorties.getVolonteAttaque();
    }
}
