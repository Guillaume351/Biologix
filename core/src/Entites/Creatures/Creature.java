package Entites.Creatures;

import Entites.Creatures.Organes.*;
import Entites.Creatures.Organes.Cerveau.Cerveau;
import Entites.Creatures.Organes.Cerveau.InputsCerveau;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Creatures.Organes.sexe.Sexe;
import Entites.Entite;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import Utils.Position.Localisable;
import Utils.Position.Localisateur;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Creature extends Entite {

    Creature embryon;

    Vector2 orientation;

    double vitesse;
    double age;
    double temperatureInterne;

    Cerveau cerveau;

    AppareilRespiratoire appareilRespiratoire;
    Bouche bouche;
    Defensif defensif;
    Digestion digestion;
    Ecailles ecailles;
    Foie foie;
    Fourrure fourrure;
    Graisse graisse;
    Offensif offensif;
    Sexe sexe;
    Mouvement mouvement;
    Perception perception;
    List<Organe> organes;
    Terrain terrain;

    public Creature(){

    }

    /**
     * Masse de la créature
     *
     * @return Masse de la créature, dépend de l'age
     */
    public double getMasse() {
        double somme = 0;
        for (Organe or : organes) {
            somme += or.getMasse(age);
        }
        return somme;
    }

    /**
     * Température interne de la créature
     *
     * @return Température Interne
     */
    public double getTemperatureInterne() {
        return temperatureInterne;
    }

    /**
     * @param dt : delta de temps de simulation
     * @return Energie dépensée pour la subsistance (rester en vie) durant le laps de temps dt
     */
    public double getCoutSubsistance(double dt) {
        double somme = 0;
        for (Organe or : organes) {
            somme += or.getCoutSubsistance(age, dt);
        }
        return somme * dt;
    }

    /**
     * Deplace la créature suivant son vecteur direction et avec sa vitesse, renvoie l'énergie dépensée
     *
     * @param dt      : delta de temps de simulation
     * @return Energie dépensée pour le déplacement réalisé
     */
    public double deplacer(double dt) {
        //deplace la creature et renvoie l'energie dépensee
        double z0 = this.terrain.getAltitudes().getValeur(getPosition());
        getPosition().add(orientation.scl((float) (dt * vitesse)));
        double z1 = terrain.getAltitudes().getValeur(getPosition());
        //Energie Potentielle
        double masse = getMasse();
        double Epot = masse * (z1 - z0) * this.terrain.getGravite();
        //Energie Cinetique
        double Ecin = mouvement.getEnergieDepenseeParUniteMasse(dt, vitesse);
        return masse * Math.min(0, Epot + Ecin);
    }

    /**
     * Pertes d'énergie dues aux échanges thermiques avec l'exterieur
     *
     * @param tempExterieure : Température exterieure
     * @param dt             : Temps durant lequel la perte est appliquee
     * @return Energie perdue durant le temps dt
     */
    public double getPertesThermiques(double tempExterieure, double dt) {
        double rth = ecailles.getResistanceThermique(tempExterieure) + fourrure.getResistanceThermique(tempExterieure) + graisse.getResistanceThermique(tempExterieure);
        return dt * Math.abs(tempExterieure - getTemperatureInterne()) / rth;
    }

    /**
     * Obtenir l'âge de la créature
     *
     * @return Age de la créature
     */
    public double getAge() {
        return this.age;
    }

    public void setAge(double age){this.age = age; }

    /**
     * Obtenir la taille de la créature
     * Se base sur la taille de l'organe le plus gros
     *
     * @return Taille de la créature
     */
    public double getTaille() {
        double result = 0;
        for (Organe or : organes) {
            result = Math.max(result, or.getTaille(age));
        }
        return result;
    }

    public Vector2 getOrientation() {
        return orientation;
    }

    public double getVitesse() {
        return vitesse;
    }

    public Cerveau getCerveau() {
        return cerveau;
    }

    public AppareilRespiratoire getAppareilRespiratoire() {
        return appareilRespiratoire;
    }

    public Bouche getBouche() {
        return bouche;
    }

    public Defensif getDefensif() {
        return defensif;
    }

    public Digestion getDigestion() {
        return digestion;
    }

    public Ecailles getEcailles() {
        return ecailles;
    }

    public Foie getFoie() {
        return foie;
    }

    public Fourrure getFourrure() {
        return fourrure;
    }

    public Graisse getGraisse() {
        return graisse;
    }

    public Offensif getOffensif() {
        return offensif;
    }

    public Sexe getSexe() {
        return sexe;
    }

    public Mouvement getMouvement() {
        return mouvement;
    }

    public Perception getPerception(){ return this.perception;}

    public List<Organe> getOrganes() {
        return organes;
    }

    public Terrain getTerrain(){ return this.terrain;}

    // TODO : implémenter cette fonction
    public float getProximiteGenetique(Creature creature) {
        return 0;
    }


    public void updateOrganes(InputsCerveau entrees, double dt){
        OutputsCerveau sortieCerveau = this.cerveau.getComportement(entrees);

        this.sexe.updateSexe(sortieCerveau, dt);
        this.offensif.updateOffensif(sortieCerveau, dt);
        this.defensif.updateDefensif(sortieCerveau, dt);
    }

    public void update_age(OutputsCerveau sortieCerveau, double dt){
        // Age
        this.setAge(this.getAge() + dt);
    }

    public double update_deplacement(Terrain terrain, OutputsCerveau sortieCerveau, double dt){
        // Deplacement
        this.orientation = sortieCerveau.getDirection();
        double energiePerdueDeplacement = this.deplacer(dt);
        return energiePerdueDeplacement;
    }

    public double update_combat(Creature creatureLaPlusProche, OutputsCerveau sortieCerveau, double dt){
        // Combattre
        double energiePerdueDefense;
        double energiePerdueAttaque;
        if (this.distance(creatureLaPlusProche) <= ConstantesBiologiques.rayonInteraction) {
            boolean perteDeVieCombat = this.foie.perteDeVieCombat(creatureLaPlusProche.offensif.getEnergieDepenseeAttaque(), this.defensif.getEnergieDepenseeDefense());
            if (!perteDeVieCombat) {
                // TODO : faire mourir la créature
            }
            energiePerdueDefense = this.defensif.getEnergieDepenseeDefense();
            energiePerdueAttaque = this.offensif.getEnergieDepenseeAttaque();
        } else {
            energiePerdueDefense = 0;
            energiePerdueAttaque = 0;
        }
        return energiePerdueAttaque + energiePerdueDefense;
    }

    public double update_manger(OutputsCerveau sortieCerveau, double dt){
        // Manger
        double coeffVoracite = sortieCerveau.getCoeffVoracite();
        List<Localisable> ressourcesAccessibles = (List) (Localisateur.getPlusProcheQue(this.getPosition(), this.perception.getRessourcessVisibles(), ConstantesBiologiques.rayonInteraction)).values();
        double energieGagneeManger = this.bouche.manger(ressourcesAccessibles, coeffVoracite);
        return energieGagneeManger;
    }

    public double update_reproduction(Creature creatureLaPlusProche, OutputsCerveau sortieCerveau, double dt){
        // Se reproduire
        double energiePerdueReproduction;
        if (this.distance(creatureLaPlusProche) <= ConstantesBiologiques.rayonInteraction) {
            double volonteReproductive = sortieCerveau.getVolonteReproductive();
            double energieDepenseeAutre = creatureLaPlusProche.getSexe().energieDepenseeReproduction();
            Sexe sexeAutre = creatureLaPlusProche.getSexe();
            boolean testReproduction = this.sexe.testReproduction(energieDepenseeAutre, sexeAutre);
            if (testReproduction) {
                energiePerdueReproduction = this.getSexe().energieDepenseeReproduction();
                this.getSexe().setEnceinte(true);
                this.getSexe().setTempsDerniereReproduction(0);
                // TODO : création de la nouvelle créature
            } else {
                energiePerdueReproduction = 0;
            }
        } else {
            energiePerdueReproduction = 0;
        }
        return energiePerdueReproduction;
    }

    public void update_accouchement(OutputsCerveau sortieCerveau, double dt){
        // Accouchement
        if (this.getSexe().getEnceinte() && this.getSexe().getTempsDerniereReproduction() == ConstantesBiologiques.tempsGestation){
            this.getSexe().setEnceinte(false);
            // TODO : perte d'énergie
            // TODO : ajout de la nouvelle créature sur la map
        }
    }

    public void update_foie(OutputsCerveau sortieCerveau, double dt){
        // Soin (Foie)
        this.foie.soin(dt);
    }

    public void update_graisse(double energieGagnee, double energiePerdue, OutputsCerveau sortieCerveau, double dt){
        // Mise à jour de l'énergie (Graisse)
        this.graisse.addEnergie(energieGagnee);
        this.graisse.subEnergie(energiePerdue);
    }

    public void update(InputsCerveau entrees, double dt, Terrain terrain){
        OutputsCerveau sortieCerveau = this.cerveau.getComportement(entrees);

        // Creature la plus proche
        List<Localisable> creaturesVisibles = this.perception.getCreaturesVisibles();
        Creature creatureLaPlusProche = (Creature) (Localisateur.getNPlusProches(this.getPosition(), creaturesVisibles, 1)).get(0);

        update_age(sortieCerveau, dt);

        double energiePerdueDeplacement = update_deplacement(terrain, sortieCerveau, dt);

        double energiePerdueCombat = update_combat(creatureLaPlusProche, sortieCerveau, dt);

        double energieGagneeManger = update_manger(sortieCerveau, dt);

        double energiePerdueReproduction = update_reproduction(creatureLaPlusProche, sortieCerveau, dt);

        update_accouchement(sortieCerveau, dt);

        update_foie(sortieCerveau, dt);

        // TODO : ajouter les pertes de subsistance et thermique
        double energiePerdue = energiePerdueDeplacement + energiePerdueReproduction + energiePerdueCombat;
        update_graisse(energieGagneeManger, energiePerdue, sortieCerveau, dt );

    }


}
