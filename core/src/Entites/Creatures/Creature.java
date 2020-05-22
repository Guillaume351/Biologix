package Entites.Creatures;

import Entites.Creatures.Organes.*;
import Entites.Creatures.Organes.Cerveau.Cerveau;
import Entites.Creatures.Organes.Cerveau.InputsCerveau;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Creatures.Organes.sexe.Genre;
import Entites.Creatures.Organes.sexe.Sexe;
import Entites.Entite;
import Entites.Ressources.Viande;
import Environnement.Meteo.Meteo;
import Environnement.Meteo.MeteoMap;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import Utils.GenerateurNom;
import Utils.Position.Localisable;
import Utils.Position.Localisateur;
import Utils.Stats.Stat;
import com.badlogic.gdx.math.Vector2;

import java.util.*;

public class Creature extends Entite {

    String nom;
    String prenom;
    Creature pere;
    Creature mere;

    List<Stat> Historique;

    public Creature(Random r, Terrain terrain) {
        super(new Vector2((float) (ConstantesBiologiques.XMAX * r.nextDouble()), (float) (ConstantesBiologiques.YMAX * r.nextDouble())));
        resetStatistiques_();
        this.pere = null;
        this.mere = null;
        this.enVie = true;
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
        this.nom = GenerateurNom.genererNomPropre(r);
        this.prenom = GenerateurNom.genererNomPropre(r);
        this.Historique = new ArrayList<>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    boolean enVie;

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
    public boolean blessure_;
    private Stat stat;
    public Creature(Creature mere, Creature pere, double mutation, Random r) {
        super(new Vector2(mere.getPosition()));
        resetStatistiques_();
        this.pere = pere;
        this.mere = mere;
        this.enVie = true;
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
        if (r.nextInt(2) == 0) {
            this.nom = pere.nom;
        } else {
            this.nom = mere.nom;
        }
        this.prenom = GenerateurNom.genererNomPropre(r);
        this.Historique = new ArrayList<>();
    }

    public Stat getStat() {
        return stat;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void resetStatistiques_() {
        reproduction_ = false;
        accouchement_ = false;
        blessure_ = false;
        stat = new Stat();
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
        double z0 = this.terrain.getAltitude(getPosition());
        getPosition().add(new Vector2(orientation).scl((float) (dt * vitesse)));
        double z1 = terrain.getAltitude(getPosition());
        //Energie Potentielle
        double masse = getMasse();
        double Epot = masse * (z1 - z0) * this.terrain.getGravite();
        //Energie Cinetique
        double Ecin = mouvement.getEnergieDepenseeParUniteMasse(dt, vitesse);

        return masse * Math.max(0, Epot + Ecin);
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


    public boolean getEnVie(){
        return this.enVie;
    }

    public double getValeurViande() {
        //TODO !!!!
        return this.getMasse() * ConstantesBiologiques.coutCroissanceRelatif;
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


    /**
     * Mettre à jour l'état des différents organes de la créature
     * @param sortieCerveau
     * @param dt
     */
    public void update_organes(OutputsCerveau sortieCerveau, double dt){
        this.sexe.updateSexe(sortieCerveau, dt);
        this.offensif.updateOffensif(sortieCerveau);
        this.defensif.updateDefensif(sortieCerveau);
        this.perception.updatePerception(sortieCerveau, dt, this.getTerrain().getMeteo().getLuminosite(terrain.getTemps()));
    }

    /**
     * Mettre à jour l'age de la créature
     * @param dt
     */
    public void update_age(double dt){
        this.setAge(this.getAge() + dt);
    }

    /**
     * Mettre à jour la position de la créature
     * @param sortieCerveau
     * @param dt
     * @return l'énergie perdue due au déplacement
     */
    public double update_deplacement(OutputsCerveau sortieCerveau, double dt){
        this.orientation = new Vector2(sortieCerveau.getDirection());
        this.vitesse = sortieCerveau.getVitesse();
        double energiePerdueDeplacement = this.deplacer(dt);
        return energiePerdueDeplacement;
    }

    /**
     * Mettre à jour la quantité d'oxygène de la créature
     * @param dt
     * @return la perte d'oxygène
     */
    public boolean update_oxygene(double dt){
        this.getAppareilRespiratoire().gainOxygene(this.getTerrain(), dt);
        return this.getAppareilRespiratoire().perteOxygene(dt);
    }

    /**
     * Mettre à jour le combat avec la créature la plus proche
     * @param creatureLaPlusProche
     * @param dt
     * @return l'énergie perdue à attaquer et défendre
     */
    public double update_combat(Creature creatureLaPlusProche, double dt) {
        double energiePerdueDefense;
        double energiePerdueAttaque;
        /* Il n'y a combat que si la créature peut voir au moins une autre créature */
        if (creatureLaPlusProche != null) {
            /* Il faut aussi que l'autre créature la plus proche soit dans le rayon d'interaction */
            if (this.distance(creatureLaPlusProche) <= ConstantesBiologiques.rayonInteraction) {
                boolean perteDeVieCombat = this.foie.perteDeVieCombat(creatureLaPlusProche.offensif.getEnergieDepenseeAttaque(dt), this.defensif.getEnergieDepenseeDefense(dt));
                /* On vérifie que la créature n'a pas perdu tous ses points de vie au combat */
                if (!perteDeVieCombat) {
                    enVie = false;
                }
                energiePerdueDefense = this.defensif.getEnergieDepenseeDefense(dt);
                energiePerdueAttaque = this.offensif.getEnergieDepenseeAttaque(dt);
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

    /**
     * Mettre à jour la nourriture que la créature mange
     * @param sortieCerveau
     * @return l'énergie gagnée en mangeant
     */
    public double update_manger(OutputsCerveau sortieCerveau){
        this.bouche.setNourritureMangee(null);
        double coeffVoracite = sortieCerveau.getCoeffVoracite();
        Collection<Localisable> ressourcesAccessiblesMap = (Localisateur.getPlusProcheQue(this.getPosition(), this.perception.getRessourcessVisibles(), ConstantesBiologiques.rayonInteraction)).values();
        ArrayList<Localisable> ressourcesAccessibles = new ArrayList<Localisable>(ressourcesAccessiblesMap);
        double energieGagneeManger = this.bouche.manger(ressourcesAccessibles, coeffVoracite);
        return energieGagneeManger;
    }

    /**
     * Mettre à jour la reproduction avec une autre créature
     * @param creatureLaPlusProche
     * @param sortieCerveau
     * @return
     */
    public double update_reproduction(Creature creatureLaPlusProche, OutputsCerveau sortieCerveau, double dt) {
        double energiePerdueReproduction;
        /* Il n'y a reproduction que si la créature peut voir au moins une autre créature */
        if (creatureLaPlusProche != null) {
            /* Il faut aussi que l'autre créature la plus proche soit dans le rayon d'interaction */
            if (this.distance(creatureLaPlusProche) <= ConstantesBiologiques.rayonInteraction) {
                double energieDepenseeAutre = creatureLaPlusProche.getSexe().energieDepenseeReproduction(dt);
                Sexe sexeAutre = creatureLaPlusProche.getSexe();
                boolean testReproduction = this.sexe.testReproduction(energieDepenseeAutre, sexeAutre, dt);
                /* Il faut que le test de reproduction soit valide */
                if (testReproduction) {
                    System.out.println("reproduction");
                    reproduction_ = true;
                    energiePerdueReproduction = this.getSexe().energieDepenseeReproduction(dt);
                    if (this.getSexe().getGenre() == Genre.Femelle) {
                        this.getSexe().setEnceinte(true);
                        this.getSexe().setTempsDerniereReproduction(0);
                        this.embryon = new Creature(this, creatureLaPlusProche, ConstantesBiologiques.mutationGenerale, new Random());
                    } else if (this.getSexe().getGenre() == Genre.Male) {
                        energiePerdueReproduction += this.getSexe().getDonEnergieEnfant();
                    }
                } else {
                    energiePerdueReproduction = 0;
                    reproduction_ = false;
                }
            } else {
                energiePerdueReproduction = 0;
            }
        } else {
            energiePerdueReproduction = 0;
        }
        return energiePerdueReproduction;
    }

    /**
     * Faire accoucher la créature
     * @return la créature qui vient de naitre de l'accouchement
     */
    public Creature accoucher() {
        System.out.println("accouchement");
        this.getSexe().setEnceinte(false);
        Creature bebe = this.embryon;
        this.embryon = null;
        return bebe;
    }

    /**
     * Mettre à jour l'accouchement d'une créature
     * @return l'énergie perdue à accoucher qui est égale à l'énergie donnée au nouveau né
     */
    public double update_accouchement(){
        double energiePerdueAccouchement;
        double delta = this.getSexe().getTempsDerniereReproduction() - ConstantesBiologiques.tempsGestation;
        /* Il n'y a accouchement que si la créature est enceinte depuis le temps de gestation */
        if (this.getSexe().getEnceinte() && delta > 0) {
            energiePerdueAccouchement = this.getSexe().getDonEnergieEnfant();
            accouchement_ = true;
            this.getTerrain().ajouterEntite(accoucher());
        } else {
            energiePerdueAccouchement = 0;
            accouchement_ = false;
        }
        return energiePerdueAccouchement;
    }

    /**
     * Mettre à jour les points de vie de la créature
     * @param creatureLaPlusProche
     * @param dt
     * @return la créature est-elle encore en vie ?
     */
    public void update_foie(Creature creatureLaPlusProche, double dt) {
        this.foie.soin(dt);
    }

    /**
     * Mettre à jour la quantité d'énergie totale de la créature
     * @param energieGagnee
     * @param energiePerdue
     * @return la créature est-elle encore en vie ?
     */
    public boolean update_graisse(double energieGagnee, double energiePerdue){
        this.graisse.addEnergie(energieGagnee);
        return this.graisse.subEnergie(energiePerdue);
    }

    /**
     * Mettre à jour l'état thermique de la créature
     * @param dt
     * @return les pertes thermiques
     */
    public double update_thermique(double dt){
        Meteo meteo = terrain.getMeteo();
        MeteoMap temperatureMap = meteo.getTemp();
        double temperature = temperatureMap.getTemp(this.getPosition().x, this.getPosition().y, terrain);
        return getPertesThermiques(temperature, dt);

    }

    /**
     * Mettre à jour l'état de la créature
     * @param entrees
     * @param dt
     * @return la créature est-elle encore en vie ?
     */
    public boolean update(InputsCerveau entrees, double dt) {

        boolean vivant = true;

        /* On calcule d'abord le comportement de la créature */
        OutputsCerveau sortieCerveau = this.cerveau.getComportement(entrees);

        /* On calcule aussi la créature la plus proche */
        List<Localisable> creaturesVisibles = this.perception.getCreaturesVisibles();
        Creature creatureLaPlusProche = null;
        List<Localisable> creaProximite = Localisateur.getNPlusProches(this.getPosition(), creaturesVisibles, 1);
        if (creaProximite.size() != 0) {
            creatureLaPlusProche = (Creature) creaProximite.get(0);
        }

        /* On met à jour l'age et les organes */
        update_age(dt);
        update_organes(sortieCerveau, dt);

        double energiePerdueSubsistance = getCoutSubsistance(dt);
        stat.setEnergiePerdueSubsistance(energiePerdueSubsistance);
        /* Mise à jour de l'oxygène */
        boolean encoreOxygene = update_oxygene(dt);

        double energiePerdueThermiquement = update_thermique(dt);
        stat.setEnergiePerdueThermiquement(energiePerdueThermiquement);
        double energiePerdueDeplacement = update_deplacement(sortieCerveau, dt);
        stat.setEnergiePerdueDeplacement(energiePerdueDeplacement);

        /* Mise à jour des points de vie */
        update_foie(creatureLaPlusProche, dt);

        double energiePerdueCombat = update_combat(creatureLaPlusProche, dt);
        stat.setEnergiePerdueCombat(energiePerdueCombat);
        double energieGagneeManger = update_manger(sortieCerveau);
        stat.setEnergieGagneeManger(energieGagneeManger);
        double energiePerdueReproduction = update_reproduction(creatureLaPlusProche, sortieCerveau, dt);
        stat.setEnergiePerdueReproduction(energiePerdueReproduction);
        double energiePerdueAccouchement = update_accouchement();
        stat.setEnergiePerdueAccouchement(energiePerdueAccouchement);
        double energiePerdue = energiePerdueThermiquement + energiePerdueSubsistance + energiePerdueDeplacement + energiePerdueDeplacement + energiePerdueReproduction + energiePerdueCombat + energiePerdueAccouchement;
        stat.setEnergieStockee(this.graisse.getEnergie());
        stat.setQuantiteOxygene(this.appareilRespiratoire.getQuantiteOxygene());
        /* Mise à jour de l'énergie */
        boolean encoreEnergie = update_graisse(energieGagneeManger, energiePerdue);

        if (!encoreOxygene || !enVie || !encoreEnergie) {
            this.enVie = false;
            vivant = false;
            System.out.println("mort");
            this.getTerrain().ajouterEntite(new Viande(this));
            this.getTerrain().retirerEntite(this);
        }
        return vivant;

    }

    @Override
    public void update(double delta_t) {
        InputsCerveau nouveauInput = new InputsCerveau(this);
        update(nouveauInput, delta_t);
        this.Historique.add(stat);
    }

    public Vector2 getPosition(int nbRendus, double dt) {
        double k = (double) (nbRendus) / ConstantesBiologiques.ratioAffichageSimulation;
        Vector2 delta = new Vector2(orientation).scl((float) (dt * vitesse * k));
        return new Vector2(this.getPosition()).add(delta);
    }
}
