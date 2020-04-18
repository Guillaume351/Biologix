package Entites.Creatures;

public abstract class Organe {
    double taille0;
    double tailleMax;
    double TempsCroissance;

    public double getTaille(double t) {
        if (t < TempsCroissance) {
            double k = (1.0 - Math.cos(Math.PI * (t / TempsCroissance))) / 2.0;
            return taille0 + k * (tailleMax - taille0);
        } else {
            return tailleMax;
        }
    }
}
