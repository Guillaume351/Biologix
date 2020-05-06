package Entites.Creatures.Organes.Cerveau;

import Entites.Creatures.Creature;
import Entites.Ressources.Ressource;
import Utils.Position.Localisable;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.LoadClass;

import java.util.ArrayList;
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
            Vector2 pointeur = ((Ressource) ress).getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl((float) getCreatureHote().getDigestion().extraireEnergie((Ressource) ress));
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurGregarite() {
        Vector2 result = new Vector2(0, 0);
        for (Localisable crea : creaturesVisibles) {
            Vector2 pointeur = ((Creature) crea).getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl(this.getCreatureHote().getProximiteGenetique((Creature) crea));
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurTailleRelative() {
        Vector2 result = new Vector2(0, 0);
        for (Localisable crea : creaturesVisibles) {
            Vector2 pointeur = ((Creature) crea).getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl((float) (this.getCreatureHote().getTaille() / ((Creature) crea).getTaille()));
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurDanger() {
        Vector2 result = new Vector2(0, 0);
        for (Localisable crea : creaturesVisibles) {
            Vector2 pointeur = ((Creature) crea).getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl((float) (((Creature) crea).getOffensif().getPuissanceAttaque() - this.getCreatureHote().getDefensif().getPuissanceDefense()));
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
