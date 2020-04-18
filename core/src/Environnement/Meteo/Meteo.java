package Environnement.Meteo;

public class Meteo {
    TemperatureMap temp;
    TemperatureMap humidite;
    double DureeJour;
    double DureeNuit;

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
