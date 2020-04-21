package Entites.Creatures.Organes;

import Entites.Creatures.Organe;

//Se reproduire
public class Sexe extends Organe {

    // TODO : on peut ajouter un attribut comme la capacité de reproduction
    private double volonteReproduction;

    // TODO : trouver un calcul pour la volonte de reproduction
    public double getVolonteReproduction(double energieDisponible, double age) {
        return volonteReproduction;
    }

    public double energieDepenseeReproduction(double energieDisponible, double age, double puissanceAttaque, double puissanceDefense, double pdv, double pdvMax){
        return getVolonteReproduction(energieDisponible, age) * (puissanceAttaque + puissanceDefense) * pdv /pdvMax;
    }

    // TODO : améliorer cette méthode ; genre y intégrer la naissance d'une créature
    // TODO : ou mettre la perte d'énergie ??
    public boolean reproduction(double energie, double energieAutre){
        return energie + energieAutre > 0;
    }


}
