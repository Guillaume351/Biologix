package Entites.Creatures.Organes;

import java.util.Random;

//Se protéger du chaud
public class Ecailles extends OrganeThermique {

    public Ecailles(Random r){
        //super(r);
    }

    public Ecailles(Ecailles ecaillesMere, Ecailles ecaillesPere, Random r){

    }

    @Override
    public double getResistanceThermique(double tempExterieure) {

        if (tempExterieure > getCreatureHote().getTemperatureInterne()) {
            return Isolation * this.getMasse(getCreatureHote().getAge()) / Math.pow(getCreatureHote().getTaille(), 2.0);
        } else {
            return 0;

        }
    }
}
