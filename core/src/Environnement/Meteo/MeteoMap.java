package Environnement.Meteo;

import Environnement.HeightMap;
import Utils.Perlin.PerlinGenerator;
import Utils.Perlin.PerlinParams;

public class MeteoMap extends HeightMap {
    HeightMap moyennes;
    PerlinParams paramsTemporel;
    double variabilite;

    public MeteoMap(PerlinParams params, double min, double max) {
        super(params, min, max);
    }


    public double getTemp(double x, double y, double t) {
        return moyennes.getValeur(x, y) + variabilite * PerlinGenerator.perlin1D(t, paramsTemporel);
    }
}
