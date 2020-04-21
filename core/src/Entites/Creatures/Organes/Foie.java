package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

//Se guérir et se soigner
public class Foie extends Organe {

    private double pointsDeVie;
    private double pointsDeVieMax;
    private double coeffDeBlessure;
    private double capaciteDeSoin;

    public double getPointsDeVie(){
        return this.pointsDeVie;
    }

    public double getPointsDeVieMax(double age){
        // TODO comment on calcule ça en fonction de l'age et de la capacite de soin
        return 0;
    }

    public double getCoeffDeBlessure(){
        return this.coeffDeBlessure;
    }

    public double getCapaciteDeSoin(){
        return this.capaciteDeSoin;
    }

    public boolean addVie(double vie, double age){
        if (this.pointsDeVie + vie > getPointsDeVieMax(age)){
            this.pointsDeVie = getPointsDeVieMax(age);
            return false;
        } else {
            this.pointsDeVie = this.pointsDeVie + vie;
            return true;
        }
    }

    public boolean subVie(double vie) {
        if (this.pointsDeVie - vie < 0) {
            this.pointsDeVie = 0;
            return false;
        } else {
            this.pointsDeVie = this.pointsDeVie - vie;
            return true;
        }
    }

     public boolean perteDeVieCombat(double energieAttaqueAdversaire , double energieDefense){
            double perteDeVie;
            double deltaEnergie = energieAttaqueAdversaire - energieDefense;
            if (deltaEnergie > 0){
                perteDeVie = deltaEnergie * this.coeffDeBlessure;
            } else {
                perteDeVie = 0;
            }
            return subVie(perteDeVie);
    }

    public void soin(){
        double deltaPDV = this.pointsDeVieMax - this.pointsDeVie;
        if (this.capaciteDeSoin < deltaPDV){
            this.pointsDeVie = this.pointsDeVie + this.capaciteDeSoin;
        } else {
            this.pointsDeVie = this.pointsDeVieMax;
        }
    }




}
