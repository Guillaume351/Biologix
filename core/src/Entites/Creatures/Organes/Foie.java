package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Utils.ConstantesBiologiques;

import java.util.Random;

//Se guérir et se soigner
public class Foie extends Organe {

    private double pointsDeVie;
    private double densiteDeSoin;   // entre min et max
    private double densiteDePv;   // entre min et max

    public Foie(Random r){
        super(r);
        this.densiteDeSoin = ConstantesBiologiques.densiteDeSoinMin + (ConstantesBiologiques.densiteDeSoinMax - ConstantesBiologiques.densiteDeSoinMin) * r.nextDouble();
        this.densiteDePv = ConstantesBiologiques.densiteDePvMin + (ConstantesBiologiques.densiteDePvMax - ConstantesBiologiques.densiteDePvMin) * r.nextDouble();
        this.pointsDeVie = this.getPointsDeVieMax0();
    }

    public Foie(Foie foieMere, Foie foiePere, Random r, double mutation){
        super(foieMere, foiePere, r, mutation);
        Foie foieAlea = new Foie(r);
        this.densiteDeSoin = (foieMere.densiteDeSoin + foiePere.densiteDeSoin + foieAlea.densiteDeSoin * mutation)/(2 + mutation);
        this.densiteDePv = (foieMere.densiteDePv + foiePere.densiteDePv + foieAlea.densiteDePv * mutation)/(2 + mutation);
        this.pointsDeVie = this.getPointsDeVieMax0();

    }

    public double getPointsDeVie(){
        return this.pointsDeVie;
    }

    public double getPointsDeVieMax() {

        return this.getMasse(this.getCreatureHote().getAge()) * this.densiteDePv;
    }

    private double getPointsDeVieMax0() {

        return this.getMasse(0) * this.densiteDePv;
    }

    public double getCapaciteDeSoin(){
        return this.densiteDeSoin * this.getCreatureHote().getMasse();
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

    /**
     * Calculer la perte de points de vie de la créature pendant un combat
     * @param energieAttaqueAdversaire
     * @param energieDefense
     * @return la créature a-t-elle encore des points de vie après le combat ?
     */
     public boolean perteDeVieCombat(double energieAttaqueAdversaire , double energieDefense){
            double perteDeVie;
            double deltaEnergie = energieAttaqueAdversaire - energieDefense;
            if (deltaEnergie > 0){
                perteDeVie = deltaEnergie * ConstantesBiologiques.coeffDeBlessure;
                this.getCreatureHote().blessure_ = true;
            } else {
                perteDeVie = 0;
                this.getCreatureHote().blessure_ = false;
            }
            return subVie(perteDeVie);

     }

    /**
     * Gagner des points de vie naturellement au fil du temps
     * @param dt
     */
    public void soin(double dt){
        double deltaPDV = this.getPointsDeVieMax() - this.pointsDeVie;
        if (this.getCapaciteDeSoin() < deltaPDV) {
            this.pointsDeVie = this.pointsDeVie + this.getCapaciteDeSoin() * dt;
        } else {
            this.pointsDeVie = this.getPointsDeVieMax() * dt;
        }
    }

    public double getProportionPv() {
        return this.getPointsDeVie() / this.getPointsDeVieMax();
    }


}
