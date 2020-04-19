package Entites.Creatures.Organes;

//Stocker l'énergie
public class Graisse extends OrganeThermique {
    double energie;
    double efficaciteEnergetique;

    //Masse = Taille*Densite
    //Energie = Taille*EfficaciteEnergetique

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
     * @param age : age de la creature
     * @return Quantite max d'energie pouvant etre stockee dans cet organe
     */
    public double getEnergieMaxStockable(double age) {
        return getTailleMaxActuel(age) * efficaciteEnergetique;
    }

    /**
     * Stocker dans cet organe de l'energie sous forme de graisse
     * @param nrj Energie a stocker
     * @param age Age de la creature
     * @return Indique si toute l'energie a pu être stockée au vu de la taille max de la graisse
     */
    public boolean addEnergie(double nrj, double age) {
        //Stocke de l'energie
        if (nrj + energie > getEnergieMaxStockable(age)) {
            //On ne peut pas stocker toute l'energie, l'organe dépasserait sa taille max;
            energie = getEnergieMaxStockable(age);
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
}
