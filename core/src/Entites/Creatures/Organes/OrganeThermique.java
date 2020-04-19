package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

public abstract class OrganeThermique extends Organe {
    double Isolation;

    public abstract double getResistanceThermique(double tempExterieure);

}
