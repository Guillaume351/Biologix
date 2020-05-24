package Entites.Creatures.Organes;

import Entites.Creatures.Organe;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;

import java.util.Random;

//Respiration Aquatique et Terrestre
public class AppareilRespiratoire extends Organe {

    double potentielBranchie;   // entre 0 et 1
    double densiteOxygene;  // entre min et max
    double quantiteOxygene;

    public AppareilRespiratoire(Random r){
        super(r);
        this.potentielBranchie = r.nextDouble();
        this.densiteOxygene = ConstantesBiologiques.densiteOxygeneMin + (ConstantesBiologiques.densiteOxygeneMax - ConstantesBiologiques.densiteOxygeneMin) * r.nextDouble();
        this.quantiteOxygene = getQuantiteOxygeneMax0();
    }

    public AppareilRespiratoire(AppareilRespiratoire arMere, AppareilRespiratoire arPere, Random r, double mutation){
        super(arMere, arPere, r, mutation);
        AppareilRespiratoire apAlea = new AppareilRespiratoire(r);
        this.potentielBranchie = (arMere.potentielBranchie + arPere.potentielBranchie + apAlea.potentielBranchie * mutation) / (2 + mutation);
        this.densiteOxygene = (arMere.densiteOxygene + arPere.densiteOxygene + apAlea.densiteOxygene * mutation) / (2 + mutation);
        this.quantiteOxygene = getQuantiteOxygeneMax0();
    }


    public double getQuantiteOxygene() {
        return this.quantiteOxygene;
    }

    public double getQuantiteOxygeneMax(){
        double retour = this.getMasse(this.getCreatureHote().getAge()) * this.densiteOxygene;
        return retour;
    }

    private double getQuantiteOxygeneMax0() {
        return this.getMasse(0) * this.densiteOxygene;
    }


    public double getPourcentageOxygene() {
        return getQuantiteOxygene() / getQuantiteOxygeneMax();

    }

    /**
     * Calculer une perte d'oxygène
     * @param dt
     * @return la creéature a-t-elle encore de l'oxygène ?
     */
    public boolean perteOxygene(double dt){
        double pertePotentielle = this.getCreatureHote().getMasse() * ConstantesBiologiques.rapportMasseConsommationOxygene * dt;
        if (this.quantiteOxygene - pertePotentielle < 0){
            this.quantiteOxygene = 0;
            return false;
        }
        else {
            this.quantiteOxygene -= pertePotentielle;
            return true;
        }
    }

    /**
     * Calculer un gain d'oxygène
     * @param terrain
     * @param dt
     * @return la créature a-t-elle atteint son maximum d'oxygène ?
     */
    public boolean gainOxygene(Terrain terrain, double dt){
        double gainPotentiel;
        if (terrain.estDansEau(this.getCreatureHote())){
            gainPotentiel = this.getMasse(this.getCreatureHote().getAge()) * potentielBranchie * ConstantesBiologiques.rapportMasseProductionOxygene * dt;
        } else {
            gainPotentiel = this.getMasse(this.getCreatureHote().getAge()) * (1 - potentielBranchie) * ConstantesBiologiques.rapportMasseProductionOxygene * dt;
        }
        if (this.quantiteOxygene + gainPotentiel > this.getQuantiteOxygeneMax()){
            this.quantiteOxygene = this.getQuantiteOxygeneMax();
            return false;
        } else {
            this.quantiteOxygene += gainPotentiel;
            return true;
        }
    }

    public boolean estAquatique() {
        return (potentielBranchie > 0.5);
    }

    public boolean estTerrestre() {
        return potentielBranchie <= 0.5;
    }

    /**
     * Determiner si la créature est dans son milieu naturel
     * @param terrain
     * @return la créature est elle dans son milieu naturel ?
     */
    public boolean estDansMilieuNaturel(Terrain terrain) {
        boolean dansEau = terrain.estDansEau(this.getCreatureHote());
        return dansEau && this.estAquatique() || !dansEau && this.estTerrestre();
    }

    /**
     * Determiner si une créature est en détresse respiratoire
     * @param terrain
     * @return le pourcentage d'oxygène perdu
     */
    public double detresseRespiratoire(Terrain terrain) {
        if (!estDansMilieuNaturel(terrain)) {
            return 1.0 - getPourcentageOxygene();
        } else {
            return 0;
        }
    }
}
