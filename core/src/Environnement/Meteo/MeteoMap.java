package Environnement.Meteo;

import Environnement.HeightMap;
import Environnement.Terrain.Terrain;
import Utils.Perlin.PerlinGenerator;
import Utils.Perlin.PerlinParams;
import Utils.Updatable;

public class MeteoMap implements Updatable {
    HeightMap moyennes;
    PerlinParams paramsTemporel;
    double variabilite;
    //coefficient modificateur de la meteo, il evolue avec dt, permet à la temperature d'evoluer
    double coefTemp = 1.0;

    public MeteoMap(PerlinParams params, double min, double max) {
        //TODO : faire la Heightmap des moyennes
        this.moyennes = new HeightMap(params, -30, 40);
        //TODO : modifier les param !!!!
        this.paramsTemporel = params;
    }

    public double getTemp(double x, double y, Terrain terrain) {
        return getTemp(x, y, terrain.getTemps());//TODO : REMPLACER PAR LE TEMPS !
    }

    public double getTemp(double x, double y, double t) {
        return coefTemp * (moyennes.getValeur(x, y) + variabilite * PerlinGenerator.perlin1D(t, paramsTemporel));
    }


    @Override
    public void update(double delta_t) {

    }
}
