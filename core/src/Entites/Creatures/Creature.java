package Entites.Creatures;

import Entites.Creatures.Organes.*;
import Entites.Creatures.Organes.Cerveau.Cerveau;
import Entites.Entite;
import Environnement.Terrain.Terrain;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Creature extends Entite {
    Vector2 orientation;
    double vitesse;
    double age;

    double temperatureInterne;

    public double getTemperatureInterne() {
        return temperatureInterne;
    }


    Cerveau cerveau;


    AppareilRespiratoire appareilRespiratoire;
    Bouche bouche;
    Defensif defensif;
    Digestion digestion;
    Ecailles ecailles;
    Foie foie;
    Fourrure fourrure;
    Graisse graisse;
    Offensif offensif;
    Sexe sexe;
    Mouvement mouvement;
    List<Organe> organes;

    /**
     * Masse de la créature
     *
     * @return Masse de la créature, dépend de l'age
     */
    public double getMasse() {
        double somme = 0;
        for (Organe or : organes) {
            somme += or.getMasse(age);
        }
        return somme;
    }

    /**
     * @param dt : delta de temps de simulation
     * @return Energie dépensée pour la subsistance (rester en vie) durant le laps de temps dt
     */
    public double getCoutSubsistance(double dt) {
        double somme = 0;
        for (Organe or : organes) {
            somme += or.getCoutSubsistance(age);
        }
        return somme;
    }

    /**
     * Deplace la créature suivant son vecteur direction et avec sa vitesse, renvoie l'énergie dépensée
     *
     * @param dt      : delta de temps de simulation
     * @param terrain : terrain sur lequel évolue la créature
     * @return Energie dépensée pour le déplacement réalisé
     */
    public double deplacer(double dt, Terrain terrain) {
        //deplace la creature et renvoie l'energie dépensee
        double z0 = terrain.getAltitudes().getValeur(getPosition());
        getPosition().add(orientation.scl((float) (dt * vitesse)));
        double z1 = terrain.getAltitudes().getValeur(getPosition());
        //Energie Potentielle
        double masse = getMasse();
        double Epot = masse * (z1 - z0) * terrain.getGravite();
        //Energie Cinetique
        double Ecin = mouvement.getEnergieDepenseeParUniteMasse(dt, vitesse);
        return masse * Math.min(0, Epot + Ecin);
    }

    /**
     * Pertes d'energie dues aux echanges thermiques avec l'exterieur
     *
     * @param tempExterieure : temperature exterieure
     * @param dt             : temps durant lequel la perte est appliquee
     * @return energie perdue durant le temps dt
     */
    public double getPertesThermiques(double tempExterieure, double dt) {
        double rth = ecailles.getResistanceThermique(tempExterieure) + fourrure.getResistanceThermique(tempExterieure) + graisse.getResistanceThermique(tempExterieure);
        return dt * Math.abs(tempExterieure - getTemperatureInterne()) / rth;
    }

    public double getAge() {
        return age;
    }

    public double getTaille() {
        double result = 0;
        for (Organe or : organes) {
            result = Math.max(result, or.getTaille(age));
        }
        return result;
    }
}
