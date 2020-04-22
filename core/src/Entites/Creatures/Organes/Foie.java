package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

//Se guérir et se soigner
public class Foie extends Organe {

    private double pointsDeVie;
    private double pointsDeVieMax;
    private double coeffDeBlessure;
    private double capaciteDeSoin;
    private double densitePv;

    public double getPointsDeVie(){
        return this.pointsDeVie;
    }

    public double getPointsDeVieMax() {
        return this.getMasse(this.getCreatureHote().getAge()) * densitePv;
    }

    public double getCoeffDeBlessure(){
        return this.coeffDeBlessure;
    }

    public double getCapaciteDeSoin(){
        return this.capaciteDeSoin;
    }

    /**
     * Ajoute une quantite de vie a une creature
     *
     * @param vie : quantite de vie a ajouter
     * @return return false si tous les points de vie n'ont pas pu etre ajoutés
     */
    public boolean addVie(double vie) {
        if (this.pointsDeVie + vie > getPointsDeVieMax()) {
            this.pointsDeVie = getPointsDeVieMax();
            return false;
        } else {
            this.pointsDeVie = this.pointsDeVie + vie;
            return true;
        }
    }

    public void soigner() {
        this.pointsDeVie = this.getPointsDeVieMax();
    }

    /**
     * Soustrait une quantite de vie a la creature
     * @param vie : vie perdue
     * @return retourne false si tous les points de vie n'ont pas pu etre retires
     */
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

    public void soin(double dt){
        double deltaPDV = this.pointsDeVieMax - this.pointsDeVie;
        if (this.capaciteDeSoin < deltaPDV) {
            this.pointsDeVie = this.pointsDeVie + this.capaciteDeSoin*dt;
        } else {
            this.pointsDeVie = this.pointsDeVieMax*dt;
        }
    }

    public double getProportionPv() {
        return this.getPointsDeVie() / this.getPointsDeVieMax();
    }


}
