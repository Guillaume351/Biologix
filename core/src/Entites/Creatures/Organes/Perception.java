package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

public class Perception extends Organe {
    double luminositeIdeale;

    public double getAdaptationLumiere(double Luminosite) {
        double adapt = 1.0 - Math.abs(Luminosite - luminositeIdeale);
        return (1.0 - Math.cos(Math.PI * adapt)) / 2.0;
    }

}
