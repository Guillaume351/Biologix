package Entites.Creatures.Organes.Cerveau;

import Entites.Creatures.Organes.Perception;

public class Cerveau {

    double gloutonerie;
    double peur;
    double agressivite;
    double gregarite;
    double bravoure;
    double prevoyance;
    double mobilite;
    double envie_reproductive;

    /**
     * Definit le comportement de la creature en fonction de sa perception et de tous les signaux d'entree
     *
     * @param entrees    ensemble de ce qui peut etre percu par la creature
     * @param perception organe qui definit la qualite et les choix de perception de la creature
     * @return ensemble des elements qui decrivent les actions de la creature
     */
    public OutputsCerveau getComportement(InputsCerveau entrees, Perception perception) {
        return new OutputsCerveau();
    }
}
