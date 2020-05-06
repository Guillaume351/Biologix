package Entites.Creatures;

import Utils.ConstantesBiologiques;

import java.util.Random;

public abstract class Organe {
    double taille0;
    double tailleMax;
    double tempsCroissance;
    double densite;
    double coutSubsistanceRelatif;

    Creature creatureHote;

    /*
    public Organe(Random r){
        this.taille0 = ConstantesBiologiques.tailleOrganeMin + (ConstantesBiologiques.tailleOrganeMax - ConstantesBiologiques.tailleOrganeMin)*r.nextDouble();
        this.tailleMax = this.taille0 + (ConstantesBiologiques.tailleOrganeMax - this.taille0)*r.nextDouble();
        this.tempsCroissance = ConstantesBiologiques.tempsCroissanceMin + (ConstantesBiologiques.tempsCroissanceMax - ConstantesBiologiques.tempsCroissanceMin)*r.nextDouble();
        this.densite = ConstantesBiologiques.densiteMin + (ConstantesBiologiques.densiteMax - ConstantesBiologiques.densiteMin)*r.nextDouble();
        this.coutSubsistanceRelatif = ConstantesBiologiques.coutSubsistanceRelatifMin + (ConstantesBiologiques.coutSubsistanceRelatifMax - ConstantesBiologiques.coutSubsistanceRelatifMin)*r.nextDouble();
    }

    public Organe(Organe organePere, Organe organeMere, double mutation, Random r){

    }
     */

    public Creature getCreatureHote() {
        return creatureHote;
    }

    /**
     * Taille de l'organe pour un age donne
     *
     * @param t : Age de la creature
     * @return Taille de l'organe pour l'age donné
     */
    public double getTaille(double t) {
        if (t < tempsCroissance) {
            double k = (1.0 - Math.cos(Math.PI * (t / tempsCroissance))) / 2.0;
            return taille0 + k * (tailleMax - taille0);
        } else {
            return tailleMax;
        }
    }

    /**
     * Masse de l'organe pour un age donné
     *
     * @param t : Age de la créature
     * @return Masse de la creature
     */
    public double getMasse(double t) {
        return getTaille(t) * densite;
    }

    /**
     * Indique le cout de subsistance par unité de temps de la créature
     * @param t : Age de la creature
     * @return Energie dediee a la subsistance de l'organe par unite de temps
     */
    public double getCoutSubsistance(double t) {
        return getTaille(t) * coutSubsistanceRelatif;
    }

    public double getTaille0() {
        return taille0;
    }

    public void setTaille0(double taille0) {
        this.taille0 = taille0;
    }

    public double getTailleMax() {
        return tailleMax;
    }

    public void setTailleMax(double tailleMax) {
        this.tailleMax = tailleMax;
    }

    public double getTempsCroissance() {
        return tempsCroissance;
    }

    public void setTempsCroissance(double tempsCroissance) {
        tempsCroissance = tempsCroissance;
    }

    public double getDensite() {
        return densite;
    }

    public void setDensite(double densite) {
        this.densite = densite;
    }
}
