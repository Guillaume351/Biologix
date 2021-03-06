package Entites.Creatures.Organes;

import Utils.ConstantesBiologiques;

import java.util.Random;

//Stocker l'énergie
public class Graisse extends OrganeThermique {
    double energie;
    double efficaciteEnergetique;

    //Masse = Taille*Densite
    //Energie = Taille*EfficaciteEnergetique


    public Graisse(Random r){
        super(r);
        this.efficaciteEnergetique = ConstantesBiologiques.efficaciteEnrgetiqueMin + (ConstantesBiologiques.efficaciteEnergetiqueMax - ConstantesBiologiques.efficaciteEnrgetiqueMin) * r.nextDouble();
        this.energie = getEnergieMaxStockable0();
    }

    public Graisse(Graisse graisseMere, Graisse graissePere, Random r, double mutation){
        super(graisseMere, graissePere, r, mutation);
        Graisse alea = new Graisse(r);
        this.efficaciteEnergetique = (graisseMere.efficaciteEnergetique + graissePere.efficaciteEnergetique + alea.efficaciteEnergetique * mutation) / (2 + mutation);
        this.energie = 0;
        this.addEnergie0((graisseMere.getCreatureHote().getSexe().getDonEnergieEnfant() + graissePere.getCreatureHote().getSexe().getDonEnergieEnfant()) * ConstantesBiologiques.rendementAccouchement);
    }

    /**
     * Renvoie la masse de graisse de la créature (ne depend pas de t)
     *
     * @param t : age de la creature
     * @return masse de graisse de la créature
     */
    @Override
    public double getMasse(double t) {
        //La masse ne dépend pas du temps mais de l'energie
        return getMasse();
    }

    /**
     * Renvoie la taille reelle de la graisse
     *
     * @param t : age de la creature (utilisez getTailleRelle pour s'en passer)
     * @return Taille de la graisse
     */
    @Override
    public double getTaille(double t) {
        return getTailleRelle();
    }


    /**
     * Taille max que peut atteindre cet organe pour un age donné
     *
     * @param t : age de la creature
     * @return Taille max de l'organe pour un age donné
     */
    public double getTailleMaxActuel(double t) {
        //TODO doit remonter jusqu a organe, a verifier !
        return super.getTaille(t);
    }

    /**
     * Taille reelle de la graisse
     *
     * @return Taille de la graisse
     */
    public double getTailleRelle() {
        return energie / efficaciteEnergetique;
    }

    /**
     * Renvoie la masse de graisse de la créature
     * @return masse de graisse de la créature
     */
    public double getMasse() {
        //La masse dépend de l energie stockée
        return getTailleRelle() * getDensite();
    }

    /**
     * Renvoie l'energie stockee sous forme de graisse
     * @return Energie stockée dans la graisse
     */
    public double getEnergie() {
        return energie;
    }

    /**
     * Renvoie la quantite d'energie maximale pouvant etre stockee dans la graisse
     *
     * @return Quantite max d'energie pouvant etre stockee dans cet organe
     */
    public double getEnergieMaxStockable() {
        return getTailleMaxActuel(this.getCreatureHote().getAge()) * efficaciteEnergetique;
    }

    private double getEnergieMaxStockable0() {
        return getTailleMaxActuel(0) * efficaciteEnergetique;
    }

    /**
     * Stocker dans cet organe de l'energie sous forme de graisse
     * @param nrj Energie a stocker
     * @return Indique si toute l'energie a pu être stockée au vu de la taille max de la graisse
     */
    public boolean addEnergie(double nrj) {
        //Stocke de l'energie
        if (nrj + energie > getEnergieMaxStockable()) {
            //On ne peut pas stocker toute l'energie, l'organe dépasserait sa taille max;
            energie = getEnergieMaxStockable();
            return false;
        } else {
            energie += nrj;
            return true;
        }
    }

    private boolean addEnergie0(double nrj) {
        //Stocke de l'energie
        if (nrj + energie > getEnergieMaxStockable0()) {
            //On ne peut pas stocker toute l'energie, l'organe dépasserait sa taille max;
            energie = getEnergieMaxStockable0();
            return false;
        } else {
            energie += nrj;
            return true;
        }
    }

    /**
     * Retirer de l'energie de cet organe
     * @param nrj Energie a retirer
     * @return Indique si toute l'energie a pu etre retirée
     */
    public boolean subEnergie(double nrj) {
        if (nrj > energie) {
            //PAs d'energie négative !
            energie = 0;
            return false;
        } else {
            energie -= nrj;
            return true;
        }
    }


    @Override
    public double getResistanceThermique(double tempExterieure) {
        return Isolation * this.getMasse(getCreatureHote().getAge()) / Math.pow(getCreatureHote().getTaille(), 2.0);
    }

    public double getProportionStocks() {
        return this.getTailleRelle() / this.getTailleMax();
    }
}
