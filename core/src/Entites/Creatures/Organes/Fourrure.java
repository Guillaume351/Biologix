package Entites.Creatures.Organes;

import java.util.Random;

//Se protéger du froid
public class Fourrure extends OrganeThermique {

    public Fourrure(Random r){
        super(r);
    }

    public Fourrure(Fourrure fourrureMere, Fourrure fourrurePere, Random r){
        super(fourrureMere, fourrurePere, r);
    }

    @Override
    public double getResistanceThermique(double tempExterieure) {
        if (tempExterieure < getCreatureHote().getTemperatureInterne()) {
            return Isolation * this.getMasse(getCreatureHote().getAge()) / Math.pow(getCreatureHote().getTaille(), 2.0);
        } else {
            return 0;

        }
    }
}
