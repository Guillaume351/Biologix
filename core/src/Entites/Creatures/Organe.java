package Entites.Creatures;

public abstract class Organe {
    double taille0;
    double tailleMax;
    double TempsCroissance;
    double densite;
    double coutSubsistanceMax;

    public double getTaille(double t) {
        if (t < TempsCroissance) {
            double k = (1.0 - Math.cos(Math.PI * (t / TempsCroissance))) / 2.0;
            return taille0 + k * (tailleMax - taille0);
        } else {
            return tailleMax;
        }
    }

    public double getMasse(double t) {
        return getTaille(t) * densite;
    }

    public double getCoutSubsistance(double t) {
        return getTaille(t) * coutSubsistanceMax;
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
        return TempsCroissance;
    }

    public void setTempsCroissance(double tempsCroissance) {
        TempsCroissance = tempsCroissance;
    }

    public double getDensite() {
        return densite;
    }

    public void setDensite(double densite) {
        this.densite = densite;
    }
}
