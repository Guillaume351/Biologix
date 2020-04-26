package Entites.Creatures.Organes.Cerveau;

import Entites.Creatures.Creature;
import Entites.Ressources.Ressource;
import Utils.Position.Localisable;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class InputsCerveau {
    List<Creature> creaturesVisibles;
    List<Ressource> ressourcesVisibles;
    private List<Localisable> entitesVisibles;
    private Creature creatureHote;

    public InputsCerveau(List<Localisable> entitesVisibles, Creature creatureHote) {
        this.entitesVisibles = entitesVisibles;
        this.creatureHote = creatureHote;
        this.creaturesVisibles = getCreaturesVisibles();
        this.ressourcesVisibles = getRessourcessVisibles();
    }

    public List<Creature> getCreaturesVisibles() {
        List<Creature> creaVisibles = new ArrayList<>();
        for (Localisable loc : entitesVisibles) {
            if (loc instanceof Creature) {
                creaVisibles.add((Creature) loc);
            }
        }
        return creaVisibles;
    }

    public List<Ressource> getRessourcessVisibles() {
        List<Ressource> ressourcesVisibles = new ArrayList<>();
        for (Localisable loc : entitesVisibles) {
            if (loc instanceof Ressource) {
                ressourcesVisibles.add((Ressource) loc);
            }
        }
        return ressourcesVisibles;
    }

    public Vector2 getVecteurNourriture() {
        Vector2 result = new Vector2(0, 0);
        for (Ressource ress : ressourcesVisibles) {
            Vector2 pointeur = ress.getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl((float) getCreatureHote().getDigestion().extraireEnergie(ress));
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurGregarite() {
        Vector2 result = new Vector2(0, 0);
        for (Creature crea : creaturesVisibles) {
            Vector2 pointeur = crea.getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl(this.getCreatureHote().getProximiteGenetique(crea));
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurTailleRelative() {
        Vector2 result = new Vector2(0, 0);
        for (Creature crea : creaturesVisibles) {
            Vector2 pointeur = crea.getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl((float) (this.getCreatureHote().getTaille() / crea.getTaille()));
            result = result.add(pointeur);
        }
        return result;
    }

    public Vector2 getVecteurDanger() {
        Vector2 result = new Vector2(0, 0);
        for (Creature crea : creaturesVisibles) {
            Vector2 pointeur = crea.getPosition().sub(getCreatureHote().getPosition());
            pointeur = pointeur.nor().scl((float) (crea.getOffensif().getPuissanceAttaque() - this.getCreatureHote().getDefensif().getPuissanceDefense()));
            result = result.add(pointeur);
        }
        return result;
    }


    public List<Localisable> getEntitesVisibles() {
        return entitesVisibles;
    }

    public void setEntitesVisibles(List<Localisable> entitesVisibles) {
        this.entitesVisibles = entitesVisibles;
    }

    public Creature getCreatureHote() {
        return creatureHote;
    }

    public void setCreatureHote(Creature creatureHote) {
        this.creatureHote = creatureHote;
    }

}
