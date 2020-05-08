package Environnement.Meteo;

import Environnement.HeightMap;
import Utils.Perlin.PerlinGenerator;
import Utils.Perlin.PerlinParams;
import Utils.Updatable;

public class MeteoMap implements Updatable {
    HeightMap moyennes;
    PerlinParams paramsTemporel;
    double variabilite;
    double temps;

    public MeteoMap(PerlinParams params, double min, double max) {
        //TODO : faire la Heightmap des moyennes
        temps = 0;
    }

    public double getTemp(double x, double y) {
        return getTemp(x, y, temps);//TODO : REMPLACER PAR LE TEMPS !
    }

    public double getTemp(double x, double y, double t) {
        return moyennes.getValeur(x, y) + variabilite * PerlinGenerator.perlin1D(t, paramsTemporel);
    }


    @Override
    public void update(int delta_t) {
        temps += delta_t;
    }
}
