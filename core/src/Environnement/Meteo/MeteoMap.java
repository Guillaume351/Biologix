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
    double coefHumidite = 1.0;
    //Moyenne issue des parametres de Perlin
    double TempMoyOrigine = 14.5;
    double HumiditeMoyOrigine = 0.5;

    /**
     * Constructeur d'une MeteoMap
     * @param params parametre pour le bruit de Perlin
     * @param min
     * @param max
     */
    public MeteoMap(PerlinParams params, double min, double max) {
        //TODO : faire la Heightmap des moyennes
        this.moyennes = new HeightMap(params, 5, 40);
        //TODO : modifier les param !!!!
        this.paramsTemporel = params;
    }

    /**
     * Renvoie la valeur de la météoMap à un endroit fix
     * @param x les coordonnés pour la localisation
     * @param y
     * @param terrain le terrrain associé
     * @return la valeur associé au point de la meteoMap
     */
    public double getTemp(double x, double y, Terrain terrain) {
        return getTemp(x, y, terrain.getTemps());
    }

    /**
     * Renvoie la valeur modulé par un coefficient qui varie selon le temps
     * @param x
     * @param y
     * @param t
     * @return la valeur à l'instant t associé au point (x,y)
     */
    public double getTemp(double x, double y, double t) {
        return coefTemp * (moyennes.getValeur(x, y) + variabilite * PerlinGenerator.perlin1D(t, paramsTemporel));
    }


    @Override
    public void update(double delta_t) {

    }

    /**
     * 30 est la temperature max et 1 est la temperature min.
     * @return Renvoie la temperature moyenne (tres arrondi)
     */
    public double getMoyenne() {
        return Math.round(coefTemp * TempMoyOrigine) ;
    }

    /**
     * Pourcentage d'humidité
     * @return le pourcentage d'humidité
     */
    public double getMoyenneHumidite() { return Math.round(coefHumidite * HumiditeMoyOrigine * 100); }

}
