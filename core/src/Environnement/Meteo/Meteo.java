package Environnement.Meteo;

import javax.swing.*;
import java.util.Random;

public class Meteo {
    private MeteoMap temp;
    private MeteoMap humidite;
    private TypeMeteo meteo;
    private double DureeJour;
    private double DureeNuit;

    //compte le nombre de dt ecoule, permet de savoir ou on en est sur une journée
    int horloge;
    boolean jour;



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
     * Permet de connaitre le nombre de dt par jour
     * @param dt
     * @return le nombre de dt par jour
     */
    public double dtParJour(double dt) {
        return this.DureeJour/dt;
    }

    /**
     * Permet de connaitre le nombre de dt par jour
     * @param dt
     * @return le nombre de dt par nuit
     */
    public double dtParNuit(double dt) {
        return this.DureeNuit/dt;
    }

    /**
     * Remet l'horloge à zero à chaque cycle
     * @param journee boolean valant vrai si on se situe sur le jour
     */
    public void nouveauJourNuit(boolean journee) {
        this.horloge = 0;
        this.jour = journee;
    }

    /**
     * represente le temps qui passe sera appele par changement meteoGlobale
     * sachant que meteoGlobale sera appelé à chaque dt
     * @param dt
     */
    public void incrementerHorloge( double dt) {

        if (this.jour) {
            if (this.horloge < (int) dtParJour(dt)) {
                this.horloge ++;
            } else {
                nouveauJourNuit(false);
            }
        } else {
            if (this.horloge < (int) dtParNuit(dt)) {
                this.horloge ++;
            } else {
                nouveauJourNuit(true);
            }
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

        double  densiteNuageFin = getDensiteNuages() + densiteNuageVoulue;
        if (densiteNuageFin <= 1 && densiteNuageFin >= 0) {
            setDensiteNuages(densiteNuageFin);
        }
    }

    /**
     * Evolution gobale de la meteo à chaque dt
     * @param dt
     */
    public void modifierMeteoGlobal(double dt) {

        Random r = new Random();
        //ecart maximum sur la map pour l'humidite et la temperature
        double humide = getHumidite().moyennes.getMax() - getHumidite().moyennes.getMin();
        double temp = getTemp().moyennes.getMax() - getTemp().moyennes.getMin();

        // la meteo globale change à chaque dt un peu
        System.out.println(getDensiteNuages());
        changerDensiteNuage(r.nextInt(5)*dt);
        changerDensiteNuage(-r.nextInt(5)*dt);
        System.out.println(getDensiteNuages());
        modifierTempGlobale(dt, r);
        incrementerHorloge(dt);

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


    /**
     * Si il fait jour et avant midi, temp augmente de meme si il fait et qu'on est apres minuit
     * Sinon elle diminue
     * @param dt
     * @param r nombre aleatoire entre 0 et 1
     */
    public void modifierTempGlobale(double dt, Random r) {
        if ((this.jour && this.horloge < (int)dtParJour(dt)/2) || (!this.jour && this.horloge > (int)dtParJour(dt)/2)) {
            getTemp().coefTemp = getTemp().coefTemp + r.nextInt(1) * dt;
        } else {
            getTemp().coefTemp = getTemp().coefTemp - r.nextInt(1) * dt;
        }
    }
    /**
     * Evolution locale de la temperature (influence d'un joueur)
     * @param x_epicentre point centrale de la modification
     * @param y_epicentre point centrale de la modification
     * @param dt
     */
    public void modifierTempLocal(double x_epicentre, double y_epicentre, double dt) {
        //TODO
    }

}
