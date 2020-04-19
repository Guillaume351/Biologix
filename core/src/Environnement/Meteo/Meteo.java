package Environnement.Meteo;

public class Meteo {
    TemperatureMap temp;
    TemperatureMap humidite;
    double DureeJour;
    double DureeNuit;

    /**
     * Indique la luminosité entre 0 et 1 en fonction du temps depuis le debut de la simulation
     *
     * @param temps : temps depuis le debut de la simulation
     * @return Luminosite entre 0 et 1
     */
    public double getLuminosite(double temps) {
        //Part du cycle jour nuit parcouru
        double tempsCycle = (temps % (DureeJour + DureeNuit));
        if (tempsCycle < DureeJour) {
            //Jour
            double k = tempsCycle / DureeJour;
            return (1.0 - Math.cos(Math.PI * 2.0 * k)) / 2.0;
        } else {
            //nuit
            return 0;
        }
    }
}
