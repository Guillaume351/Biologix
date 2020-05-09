package Environnement.Meteo;

import java.util.Random;

public class Meteo {
    private MeteoMap temp;
    private MeteoMap humidite;
    private TypeMeteo meteo;
    private double DureeJour;
    private double DureeNuit;

    public Meteo(MeteoMap temp, MeteoMap humidite, TypeMeteo meteo, double dureeJour, double dureeNuit, double densiteNuages) {
        this.temp = temp;
        this.humidite = humidite;
        this.meteo = meteo;
        DureeJour = dureeJour;
        DureeNuit = dureeNuit;
        this.densiteNuages = densiteNuages;
    }

    public MeteoMap getTemp() {
        return temp;
    }

    public MeteoMap getHumidite() {
        return humidite;
    }

    public TypeMeteo getMeteo() {
        return meteo;
    }

    public double getDureeJour() {
        return DureeJour;
    }

    public double getDureeNuit() {
        return DureeNuit;
    }

    public double getDensiteNuages() {
        return densiteNuages;
    }

    public void setDensiteNuages(double densiteNuages) {
        this.densiteNuages = densiteNuages;
    }

    /**
     * Indique la luminosité entre 0 et 1 en fonction du temps depuis le debut de la simulation
     *
     * @param temps : Temps depuis le debut de la simulation
     * @return Luminosite entre 0 et 1
     */
    public double getLuminosite(double temps) {
        //Part du cycle jour nuit parcouru
        double tempsCycle = (temps % (DureeJour + DureeNuit));
        if (tempsCycle < DureeJour) {
            //Jour
            double k = tempsCycle / DureeJour;
            return ((1.0 - Math.cos(Math.PI * 2.0 * k)) / 2.0) * (1 - densiteNuages);
        } else {
            //nuit
            return 0;
        }
    }

    /**
     * Densité des nuages (dépend de la météo : ensoleillé 0, très nuageux 1)
     */
    double densiteNuages;

    /**
     * Différents types de météo possibles
     */
    public enum TypeMeteo {
        SOLEIL, NUAGEUX, PLUIE, ORAGE, TEMPETE, NEIGE
    }


    /**
     * Change la densite de nuage
     * @param densiteNuageVoulue ce qu'on veut en terme de nuage
     */
    public void changerDensiteNuage(double densiteNuageVoulue) {

        double ecartDensiteNuage = densiteNuageVoulue - getDensiteNuages();
        setDensiteNuages(getDensiteNuages() + ecartDensiteNuage);
    }

    public void modifierMeteoGlobal(double dt) {

        Random r = new Random();
        double humide = getHumidite().moyennes.getMax() - getHumidite().moyennes.getMin();
        double temp = getTemp().moyennes.getMax() - getTemp().moyennes.getMin();

        // la meteo globale change à chaque dt un peu
        changerDensiteNuage(r.nextInt(1)*dt);

        //TODO moidifier la temperature
        if (getDensiteNuages() > 0.5) {
            this.meteo = TypeMeteo.NUAGEUX;
        } else if (getDensiteNuages() > 0.5 && humide > 0.5) {
            this.meteo = TypeMeteo.PLUIE;
        } else if (getDensiteNuages() > 0.5 && humide > 0.5 && temp > 20) {
            this.meteo = TypeMeteo.ORAGE;
        } else if (getDensiteNuages() > 0.5 && humide > 0.5 && getTemp().moyennes.getMax() < 5) {
            this.meteo = TypeMeteo.NEIGE;
        } else {
            this.meteo = TypeMeteo.SOLEIL;
        }
    }

}
