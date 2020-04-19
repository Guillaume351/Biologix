package Entites.Creatures;

public abstract class Organe {
    double taille0;
    double tailleMax;
    double TempsCroissance;
    double densite;
    double coutSubsistanceRelatif;

    /**
     * Taille de l'organe pour un age donne
     *
     * @param t : age de la creature
     * @return taille de l'organe pour l'age donné
     */
    public double getTaille(double t) {
        if (t < TempsCroissance) {
            double k = (1.0 - Math.cos(Math.PI * (t / TempsCroissance))) / 2.0;
            return taille0 + k * (tailleMax - taille0);
        } else {
            return tailleMax;
        }
    }

    /**
     * Masse de l'organe pour un age donné
     * @param t : age de la créature
     * @return masse de la creature
     */
    public double getMasse(double t) {
        return getTaille(t) * densite;
    }

    /**
     * Indique le cout de subsistance par unité de temps de la créature
     * @param t : age de la creature
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
