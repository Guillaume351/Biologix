package Entites.Creatures.Organes;

//Attaquer
public class Offensif {

    private double PuissanceAttaque;
    private double VolonteAttaque;
    Foie foie;

    public double getEnergieDepenseeAttaque(double PointsDeVie, double PointsDeVieMax, double age, double energie){
        return getVolonteAttaque(age, energie) * this.PuissanceAttaque * this.foie.getPointsDeVie() / this.foie.getPointsDeVieMax(age);
    }

    public double getVolonteAttaque(double age, double energie){
        // TODO : trouver un calcul qui prend en compte la puissance d'attaque, la vie et l'énergie de la créature
        return this.VolonteAttaque;
    }

    public double getPuissanceAttaque(){
        return this.PuissanceAttaque;
    }
}
