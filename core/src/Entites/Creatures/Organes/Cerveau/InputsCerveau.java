package Entites.Creatures.Organes.Cerveau;

import Entites.Creatures.Creature;
import Entites.Ressources.Ressource;
import Utils.Position.Localisable;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

public class InputsCerveau {
    List<Localisable> creaturesVisibles;
    List<Localisable> ressourcesVisibles;
    private Creature creatureHote;

    public InputsCerveau(Creature creatureHote) {
        this.creatureHote = creatureHote;
        this.creaturesVisibles = creatureHote.getPerception().getCreaturesVisibles();
        this.ressourcesVisibles = creatureHote.getPerception().getRessourcessVisibles();
    }


    public Vector2 getVecteurNourriture() {
        Vector2 result = new Vector2(0, 0);
        for (Localisable ress : ressourcesVisibles) {
            Vector2 pointeur = new Vector2(ress.getPosition());
            pointeur.sub(getCreatureHote().getPosition());
            double k = getCreatureHote().getDigestion().extraireEnergie((Ressource) ress) / (1.0 + pointeur.len());
            pointeur = pointeur.nor().scl((float) k);
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurGregarite() {
        Vector2 result = new Vector2(0, 0);
        for (Localisable crea : creaturesVisibles) {
            Vector2 pointeur = new Vector2(crea.getPosition());
            pointeur.sub(getCreatureHote().getPosition());
            double k = this.getCreatureHote().getProximiteGenetique((Creature) crea) / (1.0 + pointeur.len());
            if (pointeur.len() > 2 * (getCreatureHote().getTaille() + ((Creature) crea).getTaille())) {
                pointeur = pointeur.nor().scl((float) k);
                result = result.add(pointeur);
            }
        }
        return result;
    }

    public Vector2 getVecteurTailleRelative() {
        Vector2 result = new Vector2(0, 0);
        for (Localisable crea : creaturesVisibles) {
            Vector2 pointeur = new Vector2(crea.getPosition());
            pointeur.sub(getCreatureHote().getPosition());
            double k = (this.getCreatureHote().getTaille() / ((Creature) crea).getTaille()) / (1.0 + pointeur.len());
            pointeur = pointeur.nor().scl((float) k);
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurDanger() {
        Vector2 result = new Vector2(0, 0);
        for (Localisable crea : creaturesVisibles) {
            Vector2 pointeur = new Vector2(crea.getPosition());
            pointeur.sub(getCreatureHote().getPosition());
            double k = ((Creature) crea).getOffensif().getPuissanceAttaque() - this.getCreatureHote().getDefensif().getPuissanceDefense() / (1.0 + pointeur.len());
            pointeur = pointeur.nor().scl((float) k);
            result = result.add(pointeur);
        }
        return result;
    }

    public Creature getCreatureHote() {
        return creatureHote;
    }

    public void setCreatureHote(Creature creatureHote) {
        this.creatureHote = creatureHote;
    }

}
