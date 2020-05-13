package Environnement.Terrain;

import Environnement.HeightMap;
import Utils.Perlin.PerlinParams;

public class AltitudeMap extends HeightMap {

    public AltitudeMap(PerlinParams params, double min, double max) {
        super(params, min, max);
    }

    /**
     * Permet de savoir si une entitée peut voir B depuis A.
     *
     * @param A     Le point de vision
     * @param B     Le point à voir
     * @param delta Taille d'un pas
     * @param h0    Difference d'altitude entre A et B
     * @return Si il peut voir ou non B
     */


    /**
     * Donne le niveau de la mer compris entre les altitudes min et max.
     * @param pourcentageEau que l'on souhaite sur le terrain
     * @return l'altitude à partir de laquelle on n'est plus sous l'eau
     */
    public double hauteurMer(double pourcentageEau) {
        return (1 - pourcentageEau) * this.getMin() + pourcentageEau * this.getMax();
    }
}
