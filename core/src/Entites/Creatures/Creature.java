package Entites.Creatures;

import Entites.Creatures.Organes.*;
import Entites.Creatures.Organes.Cerveau.Cerveau;
import Entites.Creatures.Organes.Cerveau.InputsCerveau;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Creatures.Organes.sexe.Sexe;
import Entites.Entite;
import Environnement.Meteo.Meteo;
import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import Utils.Position.Localisable;
import Utils.Position.Localisateur;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

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

    public Creature(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())));
        this.embryon = null;
        this.orientation = new Vector2((float) r.nextDouble(), (float) r.nextDouble());
        this.vitesse = 0;
        this.age = 0;
        this.temperatureInterne = ConstantesBiologiques.tempInterneMin + (ConstantesBiologiques.tempInterneMax - ConstantesBiologiques.tempInterneMin) * r.nextDouble();
        cerveau = new Cerveau(this, r);
        appareilRespiratoire = new AppareilRespiratoire(r);
        appareilRespiratoire.setCreatureHote(this);
        bouche = new Bouche(r);
        bouche.setCreatureHote(this);
        defensif = new Defensif(r);
        defensif.setCreatureHote(this);
        digestion = new Digestion(r);
        digestion.setCreatureHote(this);
        ecailles = new Ecailles(r);
        ecailles.setCreatureHote(this);
        foie = new Foie(r);
        foie.setCreatureHote(this);
        fourrure = new Fourrure(r);
        fourrure.setCreatureHote(this);
        graisse = new Graisse(r);
        graisse.setCreatureHote(this);
        offensif = new Offensif(r);
        offensif.setCreatureHote(this);
        sexe = new Sexe(r);
        sexe.setCreatureHote(this);
        mouvement = new Mouvement(r);
        mouvement.setCreatureHote(this);
        perception = new Perception(r);
        perception.setCreatureHote(this);
        this.terrain = terrain;
        organes = Arrays.asList(appareilRespiratoire, bouche, defensif, digestion, ecailles, foie, fourrure, graisse, offensif, sexe, mouvement, perception);
    }

    public Creature(Creature mere, Creature pere, double mutation, Random r) {
        super(new Vector2(0f, 0f));
        double alea = ConstantesBiologiques.tempInterneMin + (ConstantesBiologiques.tempInterneMax - ConstantesBiologiques.tempInterneMin) * r.nextDouble();
        this.temperatureInterne = (mere.temperatureInterne + pere.temperatureInterne + alea * mutation) / (2 + mutation);
        this.embryon = null;
        this.orientation = new Vector2((float) r.nextDouble(), (float) r.nextDouble());
        this.vitesse = 0;
        this.age = 0;
        cerveau = new Cerveau(this, pere.getCerveau(), mere.getCerveau(), mutation, r);
        appareilRespiratoire = new AppareilRespiratoire(mere.getAppareilRespiratoire(), pere.getAppareilRespiratoire(), r, mutation);
        appareilRespiratoire.setCreatureHote(this);
        bouche = new Bouche(mere.getBouche(), pere.getBouche(), r, mutation);
        bouche.setCreatureHote(this);
        defensif = new Defensif(mere.getDefensif(), pere.getDefensif(), r, mutation);
        defensif.setCreatureHote(this);
        digestion = new Digestion(mere.getDigestion(), pere.getDigestion(), r, mutation);
        digestion.setCreatureHote(this);
        ecailles = new Ecailles(mere.getEcailles(), pere.getEcailles(), r, mutation);
        ecailles.setCreatureHote(this);
        foie = new Foie(mere.getFoie(), pere.getFoie(), r, mutation);
        foie.setCreatureHote(this);
        fourrure = new Fourrure(mere.getFourrure(), pere.getFourrure(), r, mutation);
        fourrure.setCreatureHote(this);
        graisse = new Graisse(mere.getGraisse(), pere.getGraisse(), r, mutation);
        graisse.setCreatureHote(this);
        offensif = new Offensif(mere.getOffensif(), pere.getOffensif(), r, mutation);
        offensif.setCreatureHote(this);
        sexe = new Sexe(mere.getSexe(), pere.getSexe(), r, mutation);
        sexe.setCreatureHote(this);
        mouvement = new Mouvement(mere.getMouvement(), pere.getMouvement(), r, mutation);
        mouvement.setCreatureHote(this);
        perception = new Perception(mere.getPerception(), pere.getPerception(), r, mutation);
        perception.setCreatureHote(this);
        this.terrain = mere.getTerrain();
        organes = Arrays.asList(appareilRespiratoire, bouche, defensif, digestion, ecailles, foie, fourrure, graisse, offensif, sexe, mouvement, perception);
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

                // TODO : faire perdre l'énergie au male
            } else {
                energiePerdueReproduction = 0;
            }
        } else {
            energiePerdueReproduction = 0;
        }
        return energiePerdueReproduction;
    }

    public double update_accouchement(OutputsCerveau sortieCerveau, double dt){
        // Accouchement
        double energiePerdueAccouchement;
        if (this.getSexe().getEnceinte() && this.getSexe().getTempsDerniereReproduction() == ConstantesBiologiques.tempsGestation){
            this.getSexe().setEnceinte(false);
            energiePerdueAccouchement = this.getSexe().getDonEnergieEnfant();
            // TODO : ajout de la nouvelle créature sur la map
        } else {
            energiePerdueAccouchement = 0;
        }
        return energiePerdueAccouchement;
    }

    public void update_foie(OutputsCerveau sortieCerveau, double dt){
        // Soin (Foie)
        this.foie.soin(dt);
    }

    public boolean update_graisse(double energieGagnee, double energiePerdue, OutputsCerveau sortieCerveau, double dt){
        // Mise à jour de l'énergie (Graisse)
        this.graisse.addEnergie(energieGagnee);
        return this.graisse.subEnergie(energiePerdue);
    }

    public double update_thermique(double dt){
        Meteo meteo = terrain.getMeteo();
        MeteoMap temperatureMap = meteo.getTemp();
        double temperature = temperatureMap.getTemp(this.getPosition().x, this.getPosition().y);
        return getPertesThermiques(temperature, dt);

    }

    public void update(InputsCerveau entrees, double dt) {
        OutputsCerveau sortieCerveau = this.cerveau.getComportement(entrees);

        // Creature la plus proche
        List<Localisable> creaturesVisibles = this.perception.getCreaturesVisibles();
        Creature creatureLaPlusProche = (Creature) (Localisateur.getNPlusProches(this.getPosition(), creaturesVisibles, 1)).get(0);

        update_age(sortieCerveau, dt);

        double energiePerdueSubsistance = getCoutSubsistance(dt);

        double energiePerdueThermiquement = update_thermique(dt);

        double energiePerdueDeplacement = update_deplacement(terrain, sortieCerveau, dt);

        double energiePerdueCombat = update_combat(creatureLaPlusProche, sortieCerveau, dt);

        double energieGagneeManger = update_manger(sortieCerveau, dt);

        double energiePerdueReproduction = update_reproduction(creatureLaPlusProche, sortieCerveau, dt);

        double energiePerdueAccouchement = update_accouchement(sortieCerveau, dt);

        update_foie(sortieCerveau, dt);

        double energiePerdue = energiePerdueThermiquement + energiePerdueSubsistance + energiePerdueDeplacement + energiePerdueDeplacement + energiePerdueReproduction + energiePerdueCombat + energiePerdueAccouchement;
        boolean vivant = update_graisse(energieGagneeManger, energiePerdue, sortieCerveau, dt);
        if (!vivant){
            // TODO : faire mourir la créature !!!
        }

    }


    @Override
    public void update(int delta_t) {
        //TODO
    }
}
