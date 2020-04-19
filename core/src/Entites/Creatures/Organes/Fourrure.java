package Entites.Creatures.Organes;

//Se protéger du froid
public class Fourrure extends OrganeThermique {

    @Override
    public double getResistanceThermique(double tempExterieure) {
        if (tempExterieure < getCreatureHote().getTemperatureInterne()) {
            return Isolation * this.getMasse(getCreatureHote().getAge()) / Math.pow(getCreatureHote().getTaille(), 2.0);
        } else {
            return 0;

        }
    }
}
