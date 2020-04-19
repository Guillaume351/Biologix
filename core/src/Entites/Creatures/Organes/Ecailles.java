package Entites.Creatures.Organes;

//Se protéger du chaud
public class Ecailles extends OrganeThermique {

    @Override
    public double getResistanceThermique(double tempExterieure) {

        if (tempExterieure > getCreatureHote().getTemperatureInterne()) {
            return Isolation * this.getMasse(getCreatureHote().getAge()) / Math.pow(getCreatureHote().getTaille(), 2.0);
        } else {
            return 0;

        }
    }
}
