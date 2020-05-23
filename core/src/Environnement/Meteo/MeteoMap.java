package Environnement.Meteo;

import Environnement.HeightMap;
import Environnement.Terrain.Terrain;
import Environnement.Terrain.TerrainInfo;
import Utils.ConstantesBiologiques;
import Utils.Perlin.PerlinGenerator;
import Utils.Perlin.PerlinParams;
import Utils.Updatable;
import com.badlogic.gdx.math.Vector2;

public class MeteoMap implements Updatable {
    HeightMap moyennes;
    PerlinParams paramsTemporel;
    double variabilite;

    //coefficient modificateur de la meteo, il evolue avec dt, permet à la temperature d'evoluer
    double coefTemp = 1.0;

    int subdivX = (int) (8 * ConstantesBiologiques.XMAX);
    int subdivY = (int) (8 * ConstantesBiologiques.YMAX);

    public MeteoMap(PerlinParams params, double min, double max) {
        //TODO : faire la Heightmap des moyennes
        this.moyennes = new HeightMap(params, 5, 40);
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

    public double getMoyenne(double t) {

        double sommeTemp = 0;
        int nbTemp = 0;
        double TempMoy;

        for (int i = 0; i < subdivX; i++) {
            for (int j = 0; j < subdivY; j++) {
                double x = (i / (double) (subdivX - 1)) * ConstantesBiologiques.XMAX;
                double y = (j / (double) (subdivY - 1)) * ConstantesBiologiques.YMAX;
                sommeTemp = sommeTemp + getTemp(x,y,t);
                nbTemp++;
            }
        }
        TempMoy = sommeTemp/nbTemp;
        return TempMoy;
    }

}
