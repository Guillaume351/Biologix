package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

//Stocker l'énergie
public class Graisse extends Organe {
    double energie;

    @Override
    public double getMasse(double t) {
        //La masse ne dépend pas du temps mais de l'energie
        return getMasse();
    }

    public double getMasse() {
        //La masse dépend de l energie stockée
        return energie * getDensite();
    }

    public double getEnergie() {
        return energie;
    }

    public boolean addEnergie(double nrj, double age) {
        //Stocke de l'energie
        if (nrj + energie > getTaille(age)) {
            //On ne peut pas stocker toute l'energie, l'organe dépasserait sa taille max;
            energie = getTaille(age);
            return false;
        } else {
            energie += nrj;
            return true;
        }
    }

    public boolean subEnergie(double nrj) {
        if (nrj > energie) {
            //PAs d'energie négative !
            energie = 0;
            return false;
        } else {
            energie -= nrj;
            return true;
        }
    }
}
