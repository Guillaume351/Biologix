package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

import java.util.Random;

public abstract class OrganeThermique extends Organe {
    double Isolation;

    public OrganeThermique(Random r){
        super(r);
        //this.Isolation = ;
    }

    public OrganeThermique(OrganeThermique otMere, OrganeThermique otPere, Random r, double mutation){
        super(otMere, otPere, r, mutation);
    }

    public abstract double getResistanceThermique(double tempExterieure);

}
