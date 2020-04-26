package Entites.Creatures.Organes.Cerveau;

import Entites.Creatures.Creature;
import com.badlogic.gdx.math.Vector2;

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
    Vector2 positionNid;

    /**
     * Definit le comportement de la creature en fonction de sa perception et de tous les signaux d'entree
     *
     * @param entrees    ensemble de ce qui peut etre percu par la creature
     * @param perception organe qui definit la qualite et les choix de perception de la creature
     * @return ensemble des elements qui decrivent les actions de la creature
     */
    public OutputsCerveau getComportement(InputsCerveau entrees) {
        OutputsCerveau retour = new OutputsCerveau();
        Vector2 Vnourriture = entrees.getVecteurNourriture();
        retour.setCoeffVoracite(normer(Vnourriture.len()));
        Vector2 Vgregarite = entrees.getVecteurGregarite();
        Vector2 VtailleRelative = entrees.getVecteurTailleRelative();
        Vector2 Vdanger = entrees.getVecteurDanger();
        retour.setVolonteAttaque(agressivite * normer(Vdanger.len() + gloutonerie * VtailleRelative.len()));
        retour.setVolonteDefense(peur * normer(Vdanger.len()));
        Vector2 Vnid = positionNid.sub(creatureHote.getPosition());

        Vector2 objectif = Vnourriture.scl((float) (gloutonerie + prevoyance * (1.0 - creatureHote.getGraisse().getProportionStocks()))).add(
                Vgregarite.scl((float) (gregarite + envie_reproductive))
        ).add(
                Vdanger.scl((float) bravoure)
        ).add(
                Vnid.scl((float) (amourDuNid * (1.0 - creatureHote.getPerception().getAdaptationLumiere())))
        ).add(
                VtailleRelative.scl((float) (gloutonerie * creatureHote.getDigestion().getRegimeAlimentaire()))
        );
        retour.setVitesse(creatureHote.getMouvement().getVitesseMax() * normer(objectif.len()));
        retour.setDirection(objectif.nor());

        return new OutputsCerveau();
    }

    public float normer(double x) {
        return (float) (1.0 - Math.exp(-x));
    }

    public void choisirNid(Vector2 position) {
        positionNid = position;
    }
}
