package Entites.Creatures.Organes.Cerveau;

import Entites.Creatures.Creature;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

public class Cerveau {

    Creature creatureHote;
    double gloutonerie;
    double peur;
    double agressivite;
    double gregarite;
    double bravoure;
    double prevoyance;
    double amourDuNid;
    double envie_reproductive;
    double comportement_amphibien;
    Vector2 positionNid;

    public Cerveau(Creature creatureHote, Random r){
        this.creatureHote = creatureHote;
        this.gloutonerie = r.nextDouble();
        this.peur = r.nextDouble();
        this.agressivite = r.nextDouble();
        this.gregarite = 2*r.nextDouble() - 1;
        this.bravoure = 2*r.nextDouble() - 1;
        this.prevoyance = r.nextDouble();
        this.amourDuNid = r.nextDouble();
        this.comportement_amphibien = r.nextDouble();
        this.envie_reproductive = r.nextDouble();
        // TODO : il faudra init la position avant le cerveau
        this.positionNid = new Vector2(creatureHote.getPosition());
    }

    public Cerveau(Creature creatureHote, Cerveau cerveauPere, Cerveau cerveauMere, double mutation, Random r) {
        this.creatureHote = creatureHote;
        this.gloutonerie = (cerveauPere.gloutonerie + cerveauMere.gloutonerie + r.nextDouble() * mutation) / (2 + mutation);
        this.peur = (cerveauPere.peur + cerveauMere.peur + r.nextDouble() * mutation) / (2 + mutation);
        this.agressivite = (cerveauPere.agressivite + cerveauMere.agressivite + r.nextDouble() * mutation) / (2 + mutation);
        this.gregarite = (cerveauPere.gregarite + cerveauMere.gregarite + (2 * r.nextDouble() - 1) * mutation) / (2 + mutation);
        this.bravoure = (cerveauPere.bravoure + cerveauMere.bravoure + (2 * r.nextDouble() - 1) * mutation) / (2 + mutation);
        this.prevoyance = (cerveauPere.prevoyance + cerveauMere.prevoyance + r.nextDouble() * mutation) / (2 + mutation);
        this.amourDuNid = (cerveauPere.amourDuNid + cerveauMere.amourDuNid + r.nextDouble() * mutation) / (2 + mutation);
        this.comportement_amphibien = (cerveauPere.comportement_amphibien + cerveauMere.comportement_amphibien + r.nextDouble() * mutation) / (2 + mutation);
        this.envie_reproductive = (cerveauPere.envie_reproductive + cerveauMere.envie_reproductive + r.nextDouble() * mutation) / (2 + mutation);
        this.positionNid = new Vector2(cerveauMere.positionNid);
        this.positionNid.lerp(cerveauPere.positionNid, r.nextFloat());
    }


    /**
     * Definit le comportement de la creature en fonction de sa perception et de tous les signaux d'entree
     * @param entrees    ensemble de ce qui peut etre percu par la creature
     * @return ensemble des elements qui decrivent les actions de la creature
     */
    public OutputsCerveau getComportement(InputsCerveau entrees) {
        OutputsCerveau retour = new OutputsCerveau();

        //Vision
        double adaptationLumiere = creatureHote.getPerception().getAdaptationLumiere();
        double champdeVisionOptimal = creatureHote.getPerception().getChampVisionOptimal();
        double distanceVisionOptimal = creatureHote.getPerception().getDistanceVisionOptimal();
        retour.setChampVision(adaptationLumiere* champdeVisionOptimal);
        retour.setDistanceVision(adaptationLumiere * distanceVisionOptimal);

        //Calcul des vecteurs
        Vector2 Vnourriture = entrees.getVecteurNourriture();
        Vector2 Vgregarite = entrees.getVecteurGregarite();
        Vector2 VtailleRelative = entrees.getVecteurTailleRelative();
        Vector2 Vdanger = entrees.getVecteurDanger();
        Vector2 Vnid = vectNid();
        Vector2 VchgtMilieu = getDirChangementMilieu();

        //Calcul des volontes
        retour.setCoeffVoracite(calcCoeffVoracite(Vnourriture, VtailleRelative));
        retour.setVolonteAttaque(calcVolonteAttaque(VtailleRelative));
        retour.setVolonteDefense(calcVolonteDefense(Vdanger));
        retour.setVolonteReproductive(calcVolonteReproductive());

        //Calcul de l'objectif
        Vector2 objectif = calcObjectif(Vnourriture, Vgregarite, Vdanger, Vnid, VtailleRelative, VchgtMilieu, retour.getVolonteReproductive());

        //Direction
        if (objectif.len() == 0) {
            retour.setDirection(new Vector2(1, 1));
        } else {
            retour.setDirection(new Vector2(objectif).nor());
        }
        //Vitesse
        retour.setVitesse(calcVitesse(objectif));


        return retour;
    }

    public Vector2 combLin(Vector2 v1, Vector2 v2, double coeff) {
        return new Vector2((float) (v1.x + coeff * v2.x), (float) (v1.y + coeff * v2.y));
    }

    public float normerV(Vector2 x) {
        return normer(x.len());
    }

    public Vector2 vectNid() {
        return new Vector2(positionNid.x - creatureHote.getPosition().x, positionNid.y - creatureHote.getPosition().y);
    }

    public float normer(double x) {
        return (float) (1.0 - Math.exp(-x));
    }

    public void choisirNid(Vector2 position) {
        positionNid = position;
    }

    public Vector2 getDirChangementMilieu() {
        double changementMilieu = 0;
        if (!creatureHote.getMouvement().estDansMilieuNaturel(creatureHote.getTerrain())) {
            changementMilieu = creatureHote.getAppareilRespiratoire().detresseRespiratoire(creatureHote.getTerrain()) * (1.0 - this.comportement_amphibien);
        } else {
            changementMilieu = this.comportement_amphibien;
        }
        Vector2 vecteurMilieu = creatureHote.getTerrain().vectPointeurChgtMilieu(creatureHote).nor();
        return new Vector2(vecteurMilieu.x * (float) changementMilieu, vecteurMilieu.y * (float) changementMilieu);
    }

    public double calcCoeffVoracite(Vector2 vecteurNourriture, Vector2 vecteurTailleRelative) {
        return normer(vecteurNourriture.len() + vecteurTailleRelative.len() * creatureHote.getDigestion().getRegimeAlimentaire());
    }

    public double calcVolonteAttaque(Vector2 vecteurTailleRelative) {
        return normer(agressivite + gloutonerie * vecteurTailleRelative.len());
    }

    public double calcVolonteDefense(Vector2 vecteurDanger) {
        return normer(vecteurDanger.len() * peur);
    }

    public double calcVolonteReproductive() {
        float sgn;
        if (creatureHote.getSexe().getEnceinte()) {
            sgn = -1.0f;
        } else {
            sgn = 1.0f;
        }
        return sgn * normer(envie_reproductive * creatureHote.getSexe().getTempsDerniereReproduction());

    }

    public double calcVitesse(Vector2 objectif) {
        return creatureHote.getMouvement().getVitesseMax(this.creatureHote.getTerrain()) * normerV(objectif);
    }

    public Vector2 calcObjectif(Vector2 vecteurNourriture, Vector2 vecteurGregarite, Vector2 vecteurDanger, Vector2 vecteurNid, Vector2 vecteurTailleRelative
            , Vector2 vecteurChangementMilieu, double volonteReproductive) {
        Vector2 objectif = Vector2.Zero;
        //Nourriture
        //double coeffNourriture = gloutonerie + prevoyance * (1.0 - creatureHote.getGraisse().getProportionStocks());
        double coeffNourriture = 1;
        Vector2 pointeurNourriture = new Vector2(vecteurNourriture).nor();
        objectif = combLin(objectif, pointeurNourriture, coeffNourriture);
        //Gregarite
        //double coeffGregarite = gregarite + volonteReproductive;
        double coeffGregarite = 0;
        Vector2 pointeurGregarite = new Vector2(vecteurGregarite).nor();
        objectif = combLin(objectif, pointeurGregarite, coeffGregarite);
        //Danger
        double coeffDanger = bravoure;
        Vector2 pointeurDanger = new Vector2(vecteurDanger).nor();
        objectif = combLin(objectif, pointeurDanger, coeffDanger);
        //Nid
        double coeffNid = amourDuNid * (1.0 - creatureHote.getPerception().getAdaptationLumiere());
        Vector2 pointeurNid = new Vector2(vecteurNid).nor();
        objectif = combLin(objectif, pointeurNid, coeffNid);
        //TailleRelative
        double coeffTailleRelative = gloutonerie * creatureHote.getDigestion().getRegimeAlimentaire();
        Vector2 pointeurTailleRelative = new Vector2(vecteurTailleRelative).nor();
        objectif = combLin(objectif, pointeurTailleRelative, coeffTailleRelative);
        objectif = combLin(objectif, vecteurChangementMilieu, 1);
        return objectif;
    }
}
