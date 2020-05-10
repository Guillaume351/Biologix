package Entites.Creatures;

import Entites.Creatures.Organes.*;
import Entites.Creatures.Organes.Cerveau.Cerveau;
import Entites.Creatures.Organes.Cerveau.InputsCerveau;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Creatures.Organes.sexe.Genre;
import Entites.Creatures.Organes.sexe.Sexe;
import Entites.Entite;
import Environnement.Meteo.Meteo;
import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import Utils.Position.Localisable;
import Utils.Position.Localisateur;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Creature extends Entite {

    Creature embryon;

    Vector2 orientation;

    double vitesse;
    double age;
    double temperatureInterne;

    Cerveau cerveau;
    OutputsCerveau OutCerveau;

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

    //Statistiques
    public boolean reproduction_;
    public boolean accouchement_;
    public boolean combat_;
    public double energieDepensee_;
    public double energieGagnee_;

    public Creature(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())));
        resetStatistiques_();
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
        OutCerveau = null;
    }

    public Creature(Creature mere, Creature pere, double mutation, Random r) {
        super(new Vector2(0f, 0f));
        resetStatistiques_();
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
        OutCerveau = null;
    }

    public void resetStatistiques_() {
        reproduction_ = false;
        accouchement_ = false;
        combat_ = false;
        energieDepensee_ = 0;
        energieGagnee_ = 0;
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
        System.out.println(this.toString() + getPosition());
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


    public double getValeurViande() {
        return 0;
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


    public void update_organes(OutputsCerveau sortieCerveau, double dt){
        this.sexe.updateSexe(sortieCerveau, dt);
        this.offensif.updateOffensif(sortieCerveau);
        this.defensif.updateDefensif(sortieCerveau);
    }

    public void update_age(double dt){
        // Age
        this.setAge(this.getAge() + dt);
    }

    public double update_deplacement(OutputsCerveau sortieCerveau, double dt){
        // Deplacement
        this.orientation = sortieCerveau.getDirection();
        double energiePerdueDeplacement = this.deplacer(dt);
        return energiePerdueDeplacement;
    }

    public boolean update_oxygene(double dt){
        this.getAppareilRespiratoire().gainOxygene(this.getTerrain(), dt);
        return this.getAppareilRespiratoire().perteOxygene(dt);
    }

    public double update_combat(Creature creatureLaPlusProche){
        // Combattre
        double energiePerdueDefense;
        double energiePerdueAttaque;
        if (creatureLaPlusProche != null) {
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
        } else {
            energiePerdueAttaque = 0;
            energiePerdueDefense = 0;
        }
        return energiePerdueAttaque + energiePerdueDefense;
    }

    public double update_manger(OutputsCerveau sortieCerveau){
        // Manger
        double coeffVoracite = sortieCerveau.getCoeffVoracite();
        Collection<Localisable> ressourcesAccessiblesMap = (Localisateur.getPlusProcheQue(this.getPosition(), this.perception.getRessourcessVisibles(), ConstantesBiologiques.rayonInteraction)).values();
        ArrayList<Localisable> ressourcesAccessibles = new ArrayList<Localisable>(ressourcesAccessiblesMap);
        double energieGagneeManger = this.bouche.manger(ressourcesAccessibles, coeffVoracite);
        return energieGagneeManger;
    }

    public double update_reproduction(Creature creatureLaPlusProche, OutputsCerveau sortieCerveau){
        // Se reproduire
        double energiePerdueReproduction;
        if (creatureLaPlusProche != null) {
            if (this.distance(creatureLaPlusProche) <= ConstantesBiologiques.rayonInteraction) {
                double volonteReproductive = sortieCerveau.getVolonteReproductive();
                double energieDepenseeAutre = creatureLaPlusProche.getSexe().energieDepenseeReproduction();
                Sexe sexeAutre = creatureLaPlusProche.getSexe();
                boolean testReproduction = this.sexe.testReproduction(energieDepenseeAutre, sexeAutre);
                if (testReproduction) {
                    energiePerdueReproduction = this.getSexe().energieDepenseeReproduction();
                    if (this.getSexe().getGenre() == Genre.Femelle) {
                        this.getSexe().setEnceinte(true);
                        this.getSexe().setTempsDerniereReproduction(0);
                        // TODO : quel facteur de mutation appliqué ?
                        this.embryon = new Creature(this, creatureLaPlusProche, 0.5, new Random());
                    } else if (this.getSexe().getGenre() == Genre.Male) {
                        energiePerdueReproduction += this.getSexe().getDonEnergieEnfant();
                    }
                } else {
                    energiePerdueReproduction = 0;
                }
            } else {
                energiePerdueReproduction = 0;
            }
        } else {
            energiePerdueReproduction = 0;
        }
        return energiePerdueReproduction;
    }

    public double update_accouchement(){
        // Accouchement
        double energiePerdueAccouchement;
        double epsilon = this.getSexe().getTempsDerniereReproduction() - ConstantesBiologiques.tempsGestation;
        if (this.getSexe().getEnceinte() && epsilon > 0) {
            this.getSexe().setEnceinte(false);
            energiePerdueAccouchement = this.getSexe().getDonEnergieEnfant();
            // TODO : ajout de la nouvelle créature sur la map
        } else {
            energiePerdueAccouchement = 0;
        }
        return energiePerdueAccouchement;
    }

    public boolean update_foie(Creature creatureLaPlusProche, double dt) {
        // Mise à jour des points de vie
        this.foie.soin(dt);
        if (creatureLaPlusProche != null) {
            if (this.distance(creatureLaPlusProche) <= ConstantesBiologiques.rayonInteraction) {
                return this.foie.perteDeVieCombat(creatureLaPlusProche.offensif.getEnergieDepenseeAttaque(), this.defensif.getEnergieDepenseeDefense());
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    public boolean update_graisse(double energieGagnee, double energiePerdue){
        // Mise à jour de l'énergie
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
        /* Créature la plus proche */
        List<Localisable> creaturesVisibles = this.perception.getCreaturesVisibles();
        Creature creatureLaPlusProche = null;
        List<Localisable> creaProximite = Localisateur.getNPlusProches(this.getPosition(), creaturesVisibles, 1);
        if (creaProximite.size() != 0) {
            creatureLaPlusProche = (Creature) creaProximite.get(0);
        }

        update_age(dt);

        update_organes(sortieCerveau, dt);

        double energiePerdueSubsistance = getCoutSubsistance(dt);

        /* Mise à jour de l'oxygène */
        boolean encoreOxygene = update_oxygene(dt);

        double energiePerdueThermiquement = update_thermique(dt);

        double energiePerdueDeplacement = update_deplacement(sortieCerveau, dt);

        /* Mise à jour des points de vie */
        boolean encorePdv = update_foie(creatureLaPlusProche,dt);

        double energiePerdueCombat = update_combat(creatureLaPlusProche);

        double energieGagneeManger = update_manger(sortieCerveau);

        double energiePerdueReproduction = update_reproduction(creatureLaPlusProche, sortieCerveau);

        double energiePerdueAccouchement = update_accouchement();

        double energiePerdue = energiePerdueThermiquement + energiePerdueSubsistance + energiePerdueDeplacement + energiePerdueDeplacement + energiePerdueReproduction + energiePerdueCombat + energiePerdueAccouchement;

        /* Mise à jour de l'énergie */
        boolean encoreEnergie = update_graisse(energieGagneeManger, energiePerdue);

        if (!encoreOxygene || !encorePdv || !encoreEnergie){
            // TODO : faire mourir la créature !!!
        }

    }


    @Override
    public void update(double delta_t) {

        update(new InputsCerveau(this), delta_t);

    }
}
