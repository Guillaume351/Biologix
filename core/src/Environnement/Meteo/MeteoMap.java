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
    double TempMoyOrigine = 22.5;


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

    /**
     * 40 est la temperature max et 5 est la temperature min.
     * @return Renvoie la temperature moyenne (tres arrondi)
     */
    public double getMoyenne() {
        return coefTemp * TempMoyOrigine;
    }

}
