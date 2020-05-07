package Entites.Creatures.Organes.sexe;

import Entites.Creatures.Organe;
import Entites.Creatures.Organes.Cerveau.InputsCerveau;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Utils.ConstantesBiologiques;

import java.util.Random;

//Se reproduire
public class Sexe extends Organe {
    double volonteReproductive; // entre 0 et 1
    double efficaciteReproductive;  // entre min et max
    Genre genre;
    boolean enceinte;
    double tempsDerniereReproduction;
    double donEnergieEnfant;    // entre 0 et 1

    public Sexe(Random r){
        super(r);
        this.volonteReproductive = r.nextDouble();
        this.efficaciteReproductive = ConstantesBiologiques.efficaciteReproductiveMin + (ConstantesBiologiques.coutSubsistanceRelatifMax - ConstantesBiologiques.coutSubsistanceRelatifMin)*r.nextDouble();
        this.genre = Genre.values()[r.nextInt(2)];
        this.enceinte = false;
        this.tempsDerniereReproduction = 0;
        this.donEnergieEnfant = r.nextDouble();
    }

    public Sexe(Sexe sexeMere, Sexe sexePere, Random r, double mutation){
        super(sexeMere,sexePere, r, mutation);
        Sexe sexeAlea = new Sexe(r);
        this.volonteReproductive = (sexeMere.volonteReproductive + sexePere.volonteReproductive + sexeAlea.volonteReproductive * mutation)/(2 + mutation);
        this.efficaciteReproductive = (sexeMere.efficaciteReproductive + sexePere.efficaciteReproductive + sexeAlea.efficaciteReproductive * mutation)/(2 + mutation);
        this.genre = Genre.values()[r.nextInt(2)];
        this.enceinte = false;
        this.tempsDerniereReproduction = 0;
        this.donEnergieEnfant = (sexeMere.donEnergieEnfant + sexePere.donEnergieEnfant + sexeAlea.donEnergieEnfant * mutation)/(2 + mutation);
    }

    public Genre getGenre() {
        return genre;
    }
    // TODO : on peut ajouter un attribut comme la capacité de reproduction

    public boolean getEnceinte(){ return this.enceinte;}

    public void setEnceinte(boolean enceinte){ this.enceinte = enceinte;}

    public double getTempsDerniereReproduction(){ return this.tempsDerniereReproduction;}

    public void setTempsDerniereReproduction(double temps){this.tempsDerniereReproduction = temps;}

    /**
     * Efficacite de chaque unite d'energie injectee dans la reproduction
     *
     * @return la capacite de reproduction
     */
    double getCapaciteReproduction() {
        return this.getMasse(this.getCreatureHote().getMasse()) * efficaciteReproductive;
    }

    public double energieDepenseeReproduction() {
        double puissanceAttaque = this.getCreatureHote().getOffensif().getPuissanceAttaque();
        double puissanceDefense = this.getCreatureHote().getDefensif().getPuissanceDefense();
        double proportionPv = this.getCreatureHote().getFoie().getProportionPv();
        return this.volonteReproductive * (puissanceAttaque + puissanceDefense) * proportionPv;
    }

    /**
     * Indique si la reproduction peut s'effectuer
     *
     * @param energieDepenseeAutre : energie engagee par l'autre creature dans la reproduction
     * @param sexeAutre            : sexe de l'autre creature
     * @return succes de la reproduction
     */
    public boolean testReproduction(double energieDepenseeAutre, Sexe sexeAutre) {
        double energieDepensee = this.energieDepenseeReproduction();
        boolean vainqueur = energieDepensee * this.getCapaciteReproduction() + energieDepenseeAutre * sexeAutre.getCapaciteReproduction() > 0;
        return !this.enceinte && vainqueur && this.getGenre() != sexeAutre.getGenre();
    }

    public void updateSexe(OutputsCerveau sorties, double dt){
        this.tempsDerniereReproduction = this.tempsDerniereReproduction + dt;
        this.volonteReproductive = sorties.getVolonteReproductive();
    }


}
