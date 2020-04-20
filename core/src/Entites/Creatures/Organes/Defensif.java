package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

//Se proteger des attaques
public class Defensif extends Organe {

    private double PuissanceDefense;
    private double VolonteDefense;
    Foie foie;

    public double getEnergieDepenseeDefense(double PointsDeVie, double PointsDeVieMax, double age, double energie){
        return getVolonteDefense(age, energie) * this.PuissanceDefense * this.foie.getPointsDeVie() / this.foie.getPointsDeVieMax(age);
    }

    public double getVolonteDefense(double age, double energie){
        // TODO : trouver un calcul qui prend en compte la puissance de défense, la vie et l'énergie de la créature
        return this.VolonteDefense;
    }

    public double getPuissanceDefense(){
        return this.PuissanceDefense;
    }
}
