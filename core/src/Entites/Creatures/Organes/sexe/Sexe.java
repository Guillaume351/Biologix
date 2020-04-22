package Entites.Creatures.Organes.sexe;

import Entites.Creatures.Organe;

//Se reproduire
public class Sexe<Genres> extends Organe {
    double efficaciteReproductive;
    Genres genre;

    public Genres getGenre() {
        return genre;
    }
    // TODO : on peut ajouter un attribut comme la capacité de reproduction

    /**
     * Efficacite de chaque unite d'energie injectee dans la reproduction
     *
     * @return la capacite de reproduction
     */
    double getCapaciteReproduction() {
        return this.getMasse(this.getCreatureHote().getMasse()) * efficaciteReproductive;
    }

    public double energieDepenseeReproduction(double volonteReproduction, double puissanceAttaque, double puissanceDefense) {
        return volonteReproduction * (puissanceAttaque + puissanceDefense) * this.getCreatureHote().getFoie().getProportionPv();
    }

    /**
     * Indique si la reproduction peut s'effectuer
     *
     * @param energieDepensee      : energie engagee dans la reproduction
     * @param energieDepenseeAutre : energie engagee par l'autre creature dans la reproduction
     * @param sexeAutre            : sexe de l'autre creature
     * @return succes de la reproduction
     */
    public boolean testReproduction(double energieDepensee, double energieDepenseeAutre, Sexe sexeAutre) {
        boolean vainqueur = energieDepensee * this.getCapaciteReproduction() + energieDepenseeAutre * sexeAutre.getCapaciteReproduction() > 0;
        return vainqueur && this.getGenre() != sexeAutre.getGenre();
    }


}
