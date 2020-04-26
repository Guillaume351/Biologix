package Entites.Creatures;

import Entites.Creatures.Organes.*;
import Entites.Creatures.Organes.Cerveau.Cerveau;
import Entites.Creatures.Organes.Cerveau.InputsCerveau;
import Entites.Creatures.Organes.Cerveau.OutputsCerveau;
import Entites.Creatures.Organes.sexe.Sexe;
import Entites.Entite;
import Entites.Ressources.Ressource;
import Environnement.Terrain.Terrain;
import Utils.ConstantesBiologiques;
import Utils.Position.Localisable;
import Utils.Position.Localisateur;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class Creature extends Entite {

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
            somme += or.getCoutSubsistance(age);
        }
        return somme;
    }

    /**
     * Deplace la créature suivant son vecteur direction et avec sa vitesse, renvoie l'énergie dépensée
     *
     * @param dt      : delta de temps de simulation
     * @param terrain : terrain sur lequel évolue la créature
     * @return Energie dépensée pour le déplacement réalisé
     */
    public double deplacer(double dt, Terrain terrain) {
        //deplace la creature et renvoie l'energie dépensee
        double z0 = terrain.getAltitudes().getValeur(getPosition());
        getPosition().add(orientation.scl((float) (dt * vitesse)));
        double z1 = terrain.getAltitudes().getValeur(getPosition());
        //Energie Potentielle
        double masse = getMasse();
        double Epot = masse * (z1 - z0) * terrain.getGravite();
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

    // TODO : implémenter cette fonction
    public float getProximiteGenetique(Creature creature) {
        return 0;
    }


    public void updateOrganes(InputsCerveau entrees, double dt){
        OutputsCerveau sortieCerveau = this.cerveau.getComportement(entrees);

        this.sexe.updateSexe(sortieCerveau, dt);
    }

    public void update(InputsCerveau entrees, double dt, Terrain terrain){
        OutputsCerveau sortieCerveau = this.cerveau.getComportement(entrees);

        // Age
        this.setAge(this.getAge() + dt);

        // Deplacement
        Vector2 direction = sortieCerveau.getDirection();
        float posX = this.getPosition().x + direction.x;
        float posY = this.getPosition().y + direction.y;
        Vector2 nouvellePos = new Vector2(posX, posY);
        this.setPosition(nouvellePos);
        this.deplacer(dt, terrain);
        // TODO : gérer la perte d'énergie sur une durée plus longue...

        // Manger
        double coeffVoracite = sortieCerveau.getCoeffVoracite();
        List<Ressource> ressourcesAccessibles = this.perception.getRessourcesAccessibles();
        double energieGagneeManger = this.bouche.manger(ressourcesAccessibles, coeffVoracite);

        // Se reproduire
        double volonteReproductive = sortieCerveau.getVolonteReproductive();
        List<Localisable> creaturesVisibles = this.perception.getCreaturesVisibles();
        Creature creatureLaPlusProche = (Creature) (Localisateur.getNPlusProches(this.getPosition(), creaturesVisibles, 1)).get(0);
        double energieDepenseeAutre = creatureLaPlusProche.getSexe().energieDepenseeReproduction();
        Sexe sexeAutre = creatureLaPlusProche.getSexe();
        boolean testReproduction = this.sexe.testReproduction(energieDepenseeAutre, sexeAutre);
        double energiePerdueReproduction;
        if (testReproduction){
            energiePerdueReproduction = this.getSexe().energieDepenseeReproduction();
            this.getSexe().setEnceinte(true);
            this.getSexe().setTempsDerniereReproduction(0);
        } else {
            energiePerdueReproduction = 0;
        }
        if (!testReproduction && this.getSexe().getEnceinte() && this.getSexe().getTempsDerniereReproduction() == ConstantesBiologiques.tempsGestation){
            this.getSexe().setEnceinte(false);
            // TODO : création de la nouvelle créature
        }

        // Accouchement

        // Combattre
    }


}
