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
        double adaptationLumiere = creatureHote.getPerception().getAdaptationLumiere();
        double champdeVisionOptimal = creatureHote.getPerception().getChampVisionOptimal();
        retour.setChampVision(adaptationLumiere* champdeVisionOptimal);
        retour.setDistanceVision(creatureHote.getPerception().getAdaptationLumiere() * creatureHote.getPerception().getDistanceVisionOptimal());
        Vector2 Vnourriture = entrees.getVecteurNourriture();
        retour.setCoeffVoracite(normer(Vnourriture.len()));
        Vector2 Vgregarite = entrees.getVecteurGregarite();
        Vector2 VtailleRelative = entrees.getVecteurTailleRelative();
        Vector2 Vdanger = entrees.getVecteurDanger();
        retour.setVolonteAttaque(agressivite * normer(Vdanger.len() + gloutonerie * VtailleRelative.len()));
        retour.setVolonteDefense(peur * normer(Vdanger.len()));
        Vector2 Vnid = new Vector2(positionNid);
        Vnid.sub(creatureHote.getPosition());
        double changementMilieu = 0;
        if (!creatureHote.getMouvement().estDansMilieuNaturel(creatureHote.getTerrain())) {
            changementMilieu = creatureHote.getAppareilRespiratoire().detresseRespiratoire(creatureHote.getTerrain()) * (1.0 - this.comportement_amphibien);
        } else {
            changementMilieu = (1.0 - creatureHote.getAppareilRespiratoire().detresseRespiratoire(creatureHote.getTerrain())) * this.comportement_amphibien;
        }
        Vector2 vecteurMilieu = new Vector2(creatureHote.getTerrain().vectPointeurChgtMilieu(creatureHote)).scl((float) changementMilieu);

        float sgn;
        if (creatureHote.getSexe().getEnceinte()) {
            sgn = -1.0f;
        } else {
            sgn = 1.0f;
        }
        retour.setVolonteReproductive(sgn * envie_reproductive * normer(creatureHote.getSexe().getTempsDerniereReproduction()));

        Vector2 objectif = Vnourriture.scl((float) (gloutonerie + prevoyance * (1.0 - creatureHote.getGraisse().getProportionStocks()))).add(
                Vgregarite.scl((float) (gregarite + retour.getVolonteReproductive()))
        ).add(
                Vdanger.scl((float) bravoure)
        ).add(
                Vnid.scl((float) (amourDuNid * (1.0 - creatureHote.getPerception().getAdaptationLumiere())))
        ).add(
                VtailleRelative.scl((float) (gloutonerie * creatureHote.getDigestion().getRegimeAlimentaire()))
        );

        retour.setVitesse(creatureHote.getMouvement().getVitesseMax(this.creatureHote.getTerrain()) * normer(objectif.len()));
        if (objectif.len() == 0 ){
            retour.setDirection(new Vector2(1, 1));
        } else {
            retour.setDirection(new Vector2(objectif.nor()));
        }

        return retour;
    }

    public float normer(double x) {
        return (float) (1.0 - Math.exp(-x));
    }

    public void choisirNid(Vector2 position) {
        positionNid = position;
    }
}
