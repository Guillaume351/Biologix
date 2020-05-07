package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;

import java.util.Random;

//Se proteger des attaques
public class Defensif extends Organe {

    private double puissanceDefense;
    private double volonteDefense;

    public Defensif(Random r){
        //super(r);
        //this.puissanceDefense = ;
        //this.volonteDefense = ;
    }

    public Defensif(Defensif defensifMere, Defensif defensifPere, Random r){

    }


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
